package ru.nsu.spirin.chessgame.move;

import ru.nsu.spirin.chessgame.board.Board;

public class MoveFactory {

    private MoveFactory() {
        throw new RuntimeException("Not instantiable");
    }

    public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
        for (final Move move : board.getAllLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
                return move;
            }
        }
        return Move.NULL_MOVE;
    }
}
