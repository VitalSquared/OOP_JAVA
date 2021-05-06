package ru.nsu.spirin.chess.move.castle;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.pieces.Rook;

public final class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(final Board board, final Piece movePiece, final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
        super(board, movePiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof QueenSideCastleMove && super.equals(other);
    }

    @Override
    public String toString() {
        return "-0-0-0-";
    }
}
