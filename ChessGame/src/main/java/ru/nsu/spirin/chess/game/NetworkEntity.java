package ru.nsu.spirin.chess.game;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.game.message.Message;
import ru.nsu.spirin.chess.game.message.MessageType;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.move.ResignMove;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public abstract class NetworkEntity extends GameEntity {
    private volatile boolean            playerReady;
    private          String             opponentName;
    private          Alliance           opponentTeam;
    private volatile boolean            opponentReady;
    private          ObjectOutputStream objectOutputStream;
    private final    Scene              scene;

    protected NetworkEntity(Scene scene, String playerName) {
        super();
        this.scene = scene;
        this.playerReady = false;
        this.opponentName = "";
        this.opponentTeam = null;
        this.opponentReady = false;
        this.setPlayerName(playerName);
    }

    public abstract ConnectionStatus connected();

    public abstract void closeConnection();

    public abstract void listenForConnections();

    public boolean isPlayerReady() {
        return this.playerReady;
    }

    public boolean isOpponentReady() {
        return this.opponentReady;
    }

    @Override
    public String getOpponentName() {
        return this.opponentName;
    }

    @Override
    public Alliance getOpponentAlliance() {
        return this.opponentTeam;
    }

    protected ObjectOutputStream getObjectOutputStream() {
        return this.objectOutputStream;
    }

    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
        sendMessage(MessageType.PLAYER_READY, playerReady);
        if (this.opponentReady && this.playerReady) {
            setBoard(Board.createStandardBoard());
            scene.setSceneState(SceneState.BOARD_MENU);
        }
    }

    @Override
    public void setPlayerAlliance(Alliance playerTeam) {
        super.setPlayerAlliance(playerTeam);
        sendMessage(MessageType.PLAYER_TEAM, playerTeam);
    }

    protected void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void sendMessage(MessageType messageType, Object content) {
        try {
            getObjectOutputStream().writeObject(new Message(messageType, content));
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

    protected final class ConnectionHandler implements Runnable {
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
