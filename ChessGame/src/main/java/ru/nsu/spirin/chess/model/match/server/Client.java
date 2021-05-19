package ru.nsu.spirin.chess.model.match.server;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.move.MoveTransition;
import ru.nsu.spirin.chess.model.move.ResignMove;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.scene.SceneState;
import ru.nsu.spirin.chess.model.match.server.message.Message;
import ru.nsu.spirin.chess.model.match.server.message.MessageType;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.utils.ThreadPool;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public final class Client extends MatchEntity {
    private volatile boolean foundOpponent;

    private volatile boolean            playerReady;
    private volatile String             opponentName;
    private volatile Alliance           opponentTeam;
    private volatile boolean            opponentReady;
    private          ObjectOutputStream objectOutputStream;
    private final Scene scene;

    private Socket  socket;
    private int     errors;
    private boolean closed;

    private final String ipAddress;
    private final int    port;

    public Client(Scene scene, String ipAddress, int port, String playerName) throws IOException {
        super(playerName);
        this.scene = scene;
        this.ipAddress = ipAddress;
        this.port = port;
        this.closed = false;
        this.foundOpponent = false;
        this.opponentName = "";
        this.opponentTeam = null;
        this.opponentReady = false;
        this.playerReady = false;
        listenForConnections();
    }

    @Override
    public String getOpponentName() {
        return this.opponentName;
    }

    @Override
    public Alliance getOpponentAlliance() {
        return this.opponentTeam;
    }

    @Override
    public boolean isOpponentReady() {
        return this.opponentReady;
    }

    @Override
    public boolean isPlayerReady() {
        return this.playerReady;
    }

    @Override
    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
        sendMessage(MessageType.PLAYER_READY, playerReady);
        if (opponentReady && playerReady) {
            setBoard(Board.createStandardBoard());
            scene.setSceneState(SceneState.BOARD_MENU);
        }
    }

    @Override
    public void setPlayerAlliance(Alliance playerTeam) {
        if (getPlayerAlliance() != playerTeam) playerReady = false;
        super.setPlayerAlliance(playerTeam);
        sendMessage(MessageType.PLAYER_READY, playerReady);
        sendMessage(MessageType.PLAYER_TEAM, playerTeam);
    }

    @Override
    public ConnectionStatus connected() {
        return errors >= 5 ?
                ConnectionStatus.FAILED :
                socket != null ?
                        foundOpponent ?
                                ConnectionStatus.CONNECTED :
                                ConnectionStatus.WAITING_FOR_PLAYER :
                        ConnectionStatus.NOT_CONNECTED;
    }

    @Override
    public void closeConnection() {
        try {
            if (this.socket != null) this.socket.close();
            this.socket = null;
            this.closed = true;
        }
        catch (IOException e) {
            System.out.println("Unable to close socket: " + e.getLocalizedMessage());
        }
    }

    public void listenForConnections() {
        this.errors = 0;
        this.socket = null;
        ThreadPool.submitThread(new Thread(() -> {
            do {
                try {
                    socket = new Socket(ipAddress, port);
                    setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
                    ThreadPool.submitThread((new ConnectionHandler(socket)));
                    sendMessage(MessageType.PLAYER_NAME, getPlayerName());
                }
                catch (IOException e) {
                    errors++;
                    socket = null;
                    System.out.println("Error in client: " + e.getLocalizedMessage());
                }
            } while (!closed && errors < 5 && socket == null);
        }));
    }

    protected void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void sendMessage(MessageType messageType, Object content) {
        try {
            objectOutputStream.writeObject(new Message(messageType, content));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeMove(Move move, MoveTransition transition) {
        applyMove(move, transition);
        sendMessage(MessageType.PLAYER_MOVE, transition);
    }

    private void applyMove(Move move, MoveTransition transition) {
        calculateScore(move);
        if (!(move instanceof ResignMove)) {
            getMoveLog().addMove(transition.getTransitionBoard(), move);
        }
        setBoard(transition.getTransitionBoard());
    }

    private final class ConnectionHandler implements Runnable {
        private final ObjectInputStream objectInputStream;
        private final Socket            socket;

        public ConnectionHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            while (getBoard() == null || !BoardUtils.isEndGame(getBoard())) {
                try {
                    if (socket.isClosed() || !socket.isConnected()) {
                        throw new SocketException("");
                    }
                    Object message = objectInputStream.readObject();
                    manageMessages((Message) message);
                }
                catch (EOFException | SocketException e) {
                    listenForConnections();
                    break;
                }
                catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        private void manageMessages(Message message) {
            switch (message.getType()) {
                case PLAYER_FOUND -> foundOpponent = true;
                case PLAYER_NAME -> opponentName = (String) message.getContent();
                case PLAYER_TEAM -> {
                    opponentTeam = (Alliance) message.getContent();
                    if (opponentTeam == getPlayerAlliance()) {
                        playerReady = false;
                        sendMessage(MessageType.PLAYER_READY, playerReady);
                    }
                }
                case PLAYER_READY -> {
                    opponentReady = (Boolean) message.getContent();
                    if (opponentReady && playerReady) {
                        setBoard(Board.createStandardBoard());
                        scene.setSceneState(SceneState.BOARD_MENU);
                    }
                }
                case PLAYER_MOVE -> {
                    MoveTransition transition = (MoveTransition) message.getContent();
                    if (getBoard().getCurrentPlayer().getAlliance() == getPlayerAlliance()) {
                        if (!(transition.getMove() instanceof ResignMove)) return;
                    }
                    applyMove(transition.getMove(), transition);
                }
            }
        }
    }
}
