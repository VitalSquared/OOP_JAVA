package ru.nsu.spirin.chess.model.match.server;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.move.MoveLog;
import ru.nsu.spirin.chess.model.move.MoveStatus;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.scene.SceneState;
import ru.nsu.spirin.chess.model.match.server.message.Message;
import ru.nsu.spirin.chess.model.match.server.message.MessageType;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.thread.ThreadPool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class Client extends MatchEntity {
    private volatile boolean foundOpponent;

    private volatile boolean            playerReady;
    private volatile String             opponentName;
    private volatile Alliance           opponentTeam;
    private volatile boolean            opponentReady;
    private          ObjectOutputStream objectOutputStream;
    private final Scene scene;

    private volatile MoveStatus serverEvaluatedMove;

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
        this.serverEvaluatedMove = null;
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
        ThreadPool.INSTANCE.submitTask(new Thread(() -> {
            do {
                try {
                    socket = new Socket(ipAddress, port);
                    setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
                    ThreadPool.INSTANCE.submitTask((new ConnectionHandler(socket)));
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
    public MoveStatus makeMove(Move move) {
        serverEvaluatedMove = null;
        sendMessage(MessageType.PLAYER_MOVE, move);
        while (serverEvaluatedMove == null) {
            Thread.onSpinWait();
        }
        MoveStatus status = serverEvaluatedMove;
        if (status.isDone()) calculateScore(move);
        serverEvaluatedMove = null;
        return status;
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
            case PLAYER_READY -> opponentReady = (Boolean) message.getContent();
            case NEW_BOARD -> {
                if (scene.getSceneState() != SceneState.BOARD_MENU) scene.setSceneState(SceneState.BOARD_MENU);

                Object[] content = (Object[]) message.getContent();
                Board board = (Board) content[0];
                MoveLog moveLog = (MoveLog) content[1];

                setBoard(board);
                setMoveLog(moveLog);

                serverEvaluatedMove = MoveStatus.DONE;
            }
            case ILLEGAL_MOVE -> serverEvaluatedMove = (MoveStatus) message.getContent();
            default -> System.out.println("Shouldn't receive such message: " + message.getType());
        }
    }

    private final class ConnectionHandler implements Runnable {
        private final ObjectInputStream objectInputStream;

        public ConnectionHandler(Socket socket) throws IOException {
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            while (getBoard() == null || !BoardUtils.isEndGame(getBoard())) {
                try {
                    Object message = objectInputStream.readObject();
                    manageMessages((Message) message);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            closeConnection();
        }
    }
}
