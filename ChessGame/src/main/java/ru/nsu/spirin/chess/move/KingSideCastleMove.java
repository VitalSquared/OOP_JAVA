package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.pieces.Rook;

public final class KingSideCastleMove extends CastleMove {
    public KingSideCastleMove(Board board, Piece movePiece, int destinationCoordinate, Rook castleRook, int castleRookDestination) {
        super(board, movePiece, destinationCoordinate, castleRook, castleRookDestination);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof KingSideCastleMove) && super.equals(other);
    }

    @Override
    public String toString() {
        return "O-O";
    }
}
