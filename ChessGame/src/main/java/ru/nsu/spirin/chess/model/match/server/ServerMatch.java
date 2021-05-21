package ru.nsu.spirin.chess.model.match.server;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.match.server.message.Message;
import ru.nsu.spirin.chess.model.match.server.message.MessageType;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.move.MoveLog;
import ru.nsu.spirin.chess.model.move.MoveStatus;
import ru.nsu.spirin.chess.model.move.MoveTransition;
import ru.nsu.spirin.chess.model.move.ResignMove;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.player.Player;
import ru.nsu.spirin.chess.thread.ThreadPool;

public final class ServerMatch {
    private volatile boolean isMatchOver;
    private volatile boolean needAnotherPlayer;
    private volatile Board   board;
    private final    MoveLog moveLog;

    private final ConnectedPlayer player1;
    private final ConnectedPlayer player2;

    public ServerMatch(ConnectedPlayer player1, ConnectedPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.isMatchOver = false;
        this.needAnotherPlayer = false;
        this.moveLog = new MoveLog();
        this.board = null;

        ThreadPool.INSTANCE.submitTask(new PlayerThread(player1, player2));
        ThreadPool.INSTANCE.submitTask(new PlayerThread(player2, player1));
    }

    public ConnectedPlayer getPlayer1() {
        return this.player1;
    }

    public ConnectedPlayer getPlayer2() {
        return this.player2;
    }

    public boolean isMatchOver() {
        return this.isMatchOver;
    }

    public boolean needAnotherPlayer() {
        return this.needAnotherPlayer;
    }

    private final class PlayerThread implements Runnable {
        private final ConnectedPlayer connectedPlayer;
        private final ConnectedPlayer otherPlayer;

        public PlayerThread(ConnectedPlayer connectedPlayer, ConnectedPlayer otherPlayer) {
            this.connectedPlayer = connectedPlayer;
            this.otherPlayer = otherPlayer;
        }

        @Override
        public void run() {
            try {
                otherPlayer.writeData(MessageType.PLAYER_NAME, connectedPlayer.getPlayerName());
            }
            catch (Exception ignored) {
            }
            while (!isMatchOver) {
                try {
                    Message message = (Message) connectedPlayer.readData();
                    if (message == null || message.getType() == MessageType.ILLEGAL_MOVE ||
                        message.getType() == MessageType.NEW_BOARD ||
                        message.getType() == MessageType.CANCEL_WAITING) continue;

                    if (message.getType() != MessageType.PLAYER_MOVE) {
                        try {
                            otherPlayer.writeData(message.getType(), message.getContent());
                        }
                        catch (Exception ignored) {
                        }
                        switch (message.getType()) {
                            case PLAYER_TEAM -> connectedPlayer.setPlayerAlliance((Alliance) message.getContent());
                            case PLAYER_READY -> {
                                connectedPlayer.setReady((Boolean) message.getContent());
                                if (connectedPlayer.isReady() && otherPlayer.isReady()) {
                                    board = Board.createStandardBoard();
                                    connectedPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, new MoveLog(moveLog) });
                                    otherPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, new MoveLog(moveLog) });
                                }
                            }
                            case PLAYER_FOUND -> {
                                boolean found = (Boolean) message.getContent();
                                if (!found) {
                                    if (!otherPlayer.getSocket().isClosed()) {
                                        connectedPlayer.getSocket().close();
                                    }
                                    needAnotherPlayer = true;
                                    return;
                                }
                            }
                        }
                    }
                    else {
                        Move move = (Move) message.getContent();
                        MoveStatus moveStatus = MoveStatus.ILLEGAL_MOVE;
                        if (move instanceof ResignMove ||
                            board.getCurrentPlayer().getAlliance() == connectedPlayer.getPlayerAlliance()) {
                            Player alliancePlayer = connectedPlayer.getPlayerAlliance().choosePlayer(board.getWhitePlayer(), board.getBlackPlayer());
                            MoveTransition transition = alliancePlayer.makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                board = transition.getTransitionBoard();
                                if (!(move instanceof ResignMove)) moveLog.addMove(board, move);
                                connectedPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, new MoveLog(moveLog) });
                                otherPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, new MoveLog(moveLog) });
                                if (BoardUtils.isEndGame(board)) {
                                    isMatchOver = true;
                                    connectedPlayer.getSocket().close();
                                    otherPlayer.getSocket().close();
                                    return;
                                }
                                continue;
                            }
                            moveStatus = transition.getMoveStatus();
                        }
                        connectedPlayer.writeData(MessageType.ILLEGAL_MOVE, moveStatus);
                    }
                }
                catch (Exception e) {
                    System.out.println("Error in server match: " + e.getLocalizedMessage());
                }
            }
        }
    }
}
