package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.pieces.Rook;

public final class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(Board board, Piece movePiece, int destinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
        super(board, movePiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof QueenSideCastleMove) && super.equals(other);
    }

    @Override
    public String toString() {
        return "-0-0-0-";
    }
}
