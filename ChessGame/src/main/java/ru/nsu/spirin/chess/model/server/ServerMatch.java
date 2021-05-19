package ru.nsu.spirin.chess.model.server;

import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.server.message.Message;
import ru.nsu.spirin.chess.model.server.message.MessageType;
import ru.nsu.spirin.chess.model.move.MoveTransition;
import ru.nsu.spirin.chess.utils.ThreadPool;

import java.io.IOException;

public final class ServerMatch {
    private boolean isMatchOver;

    public ServerMatch(ConnectedPlayer player1, ConnectedPlayer player2) throws IOException {
        this.isMatchOver = false;
        ThreadPool.submitThread(new PlayerThread(player1, player2));
        ThreadPool.submitThread(new PlayerThread(player2, player1));
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
                otherPlayer.writeData(MessageType.PLAYER_NAME, connectedPlayer.getPlayerName());
            }
            catch (Exception ignored) {}
            while (!isMatchOver) {
                try {
                    Message message = (Message) connectedPlayer.readData();
                    otherPlayer.writeData(message.getType(), message.getContent());
                    if (message.getType() == MessageType.PLAYER_MOVE) {
                        MoveTransition transition = (MoveTransition) message.getContent();
                        if (BoardUtils.isEndGame(transition.getTransitionBoard())) {
                            isMatchOver = true;
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getLocalizedMessage());
                }
            }
        }
    }
}
