package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.pieces.Piece;
import ru.nsu.spirin.chess.model.pieces.Rook;

public final class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(Board board, Piece movePiece, int destinationCoordinate, Rook castleRook, int castleRookDestination) {
        super(board, movePiece, destinationCoordinate, castleRook, castleRookDestination);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof QueenSideCastleMove) && super.equals(other);
    }

    @Override
    public String toString() {
        return "O-O-O";
    }
}
