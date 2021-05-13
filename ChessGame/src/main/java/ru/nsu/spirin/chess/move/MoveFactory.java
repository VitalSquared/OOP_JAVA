package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.player.Alliance;

public final class MoveFactory {
    private static final Move NULL_MOVE = new NullMove();

    private MoveFactory() {
        throw new RuntimeException("Not instantiable");
    }

    public static Move createMove(Board board, int currentCoordinate, int destinationCoordinate) {
        for (Move move : board.getWhitePlayer().getLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate &&
                move.getDestinationCoordinate() == destinationCoordinate) {
                return move;
            }
        }
        for (Move move : board.getBlackPlayer().getLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate &&
                move.getDestinationCoordinate() == destinationCoordinate) {
                return move;
            }
        }
        return NULL_MOVE;
    }

    public static Move createResignMove(Board board, Alliance alliance) {
        return new ResignMove(board, alliance);
    }
}
