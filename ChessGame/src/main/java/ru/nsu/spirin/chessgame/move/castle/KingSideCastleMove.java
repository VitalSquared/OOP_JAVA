package ru.nsu.spirin.chessgame.move.castle;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.pieces.Rook;

public final class KingSideCastleMove extends CastleMove {
    public KingSideCastleMove(final Board board, final Piece movePiece, final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
        super(board, movePiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof KingSideCastleMove && super.equals(other);
    }

    @Override
    public String toString() {
        return "-0-0-";
    }
}
