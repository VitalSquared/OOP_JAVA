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
    private boolean isMatchOver;
    private volatile Board   board;
    private final MoveLog moveLog;

    public ServerMatch(ConnectedPlayer player1, ConnectedPlayer player2) {
        this.isMatchOver = false;
        this.moveLog = new MoveLog();
        this.board = null;
        ThreadPool.INSTANCE.submitTask(new PlayerThread(player1, player2));
        ThreadPool.INSTANCE.submitTask(new PlayerThread(player2, player1));
    }

    public boolean isMatchOver() {
        return this.isMatchOver;
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
                otherPlayer.writeData(MessageType.PLAYER_FOUND, true);
                otherPlayer.writeData(MessageType.PLAYER_NAME, connectedPlayer.getPlayerName());
            }
            catch (Exception ignored) {}
            while (!isMatchOver) {
                try {
                    Message message = (Message) connectedPlayer.readData();
                    if (message == null || message.getType() == MessageType.ILLEGAL_MOVE || message.getType() == MessageType.NEW_BOARD) continue;

                    if (message.getType() != MessageType.PLAYER_MOVE) {
                        if (message.getType() == MessageType.PLAYER_TEAM) {
                            connectedPlayer.setPlayerAlliance((Alliance) message.getContent());
                        }
                        otherPlayer.writeData(message.getType(), message.getContent());
                        if (message.getType() == MessageType.PLAYER_READY) {
                            connectedPlayer.setReady((Boolean) message.getContent());
                            if (connectedPlayer.isReady() && otherPlayer.isReady()) {
                                board = Board.createStandardBoard();
                                connectedPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, moveLog });
                                otherPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, moveLog });
                            }
                        }
                    }
                    else {
                        Move move = (Move) message.getContent();
                        MoveStatus moveStatus = MoveStatus.ILLEGAL_MOVE;
                        if (move instanceof ResignMove || board.getCurrentPlayer().getAlliance() == connectedPlayer.getPlayerAlliance()) {
                            Player alliancePlayer = connectedPlayer.getPlayerAlliance().choosePlayer(board.getWhitePlayer(), board.getBlackPlayer());
                            MoveTransition transition = alliancePlayer.makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                board = transition.getTransitionBoard();
                                if (!(move instanceof ResignMove)) moveLog.addMove(board, move);
                                connectedPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, moveLog });
                                otherPlayer.writeData(MessageType.NEW_BOARD, new Object[] { board, moveLog });
                                if (BoardUtils.isEndGame(transition.getTransitionBoard())) {
                                    isMatchOver = true;
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
