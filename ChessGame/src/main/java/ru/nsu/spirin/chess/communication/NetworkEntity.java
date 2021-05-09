package ru.nsu.spirin.chess.communication;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.communication.message.Message;
import ru.nsu.spirin.chess.communication.message.MessageType;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.move.ResignMove;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.utils.ThreadUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class NetworkEntity extends GameEntity {
    private volatile boolean playerReady;
    private String  opponentName;
    private Alliance opponentTeam;
    private volatile boolean opponentReady;
    private ObjectOutputStream objectOutputStream;

    protected NetworkEntity(String playerName) {
        super();
        this.playerReady = false;
        this.opponentName = "";
        this.opponentTeam = null;
        this.opponentReady = false;
        setPlayerName(playerName);
        ThreadUtils.submitThread(
                new Thread(() -> {
                    while (getBoard() == null) {
                        if (isPlayerReady() && isOpponentReady()) {
                            setBoard(Board.createStandardBoard());
                        }
                    }
                })
        );
    }

    public abstract ConnectionStatus connected();

    public abstract void closeConnection();

    public boolean isPlayerReady() {
        return this.playerReady;
    }

    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
        sendMessage(MessageType.PLAYER_READY, playerReady);
    }

    @Override
    public void setPlayerTeam(Alliance playerTeam) {
        super.setPlayerTeam(playerTeam);
        sendMessage(MessageType.PLAYER_TEAM, playerTeam);
    }

    @Override
    public String getOpponentName() {
        return this.opponentName;
    }

    @Override
    public Alliance getOpponentTeam() {
        return this.opponentTeam;
    }

    public boolean isOpponentReady() {
        return this.opponentReady;
    }

    protected ObjectOutputStream getObjectOutputStream() {
        return this.objectOutputStream;
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
        if (getBoard().getCurrentPlayer().getAlliance() == getPlayerAlliance()) {
            calculateScore(move);
        }
        setBoard(transition.getTransitionBoard());
        getMoveLog().addMove(move);
    }

    protected final class ConnectionHandler implements Runnable {
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
                catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        private void manageMessages(Message message) {
            switch (message.getType()) {
                case PLAYER_NAME -> {
                    opponentName = (String) message.getContent();
                }
                case PLAYER_TEAM -> {
                    opponentTeam =  (Alliance) message.getContent();
                }
                case PLAYER_READY -> {
                    opponentReady = (Boolean) message.getContent();
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
