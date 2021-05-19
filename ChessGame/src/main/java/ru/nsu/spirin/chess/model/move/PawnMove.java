package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.pieces.Piece;

public final class PawnMove extends Move {
    public PawnMove(Board board, Piece movedPiece, int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof PawnMove) && super.equals(other);
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
