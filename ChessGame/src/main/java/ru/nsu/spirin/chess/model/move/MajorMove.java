package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.pieces.Piece;

public final class MajorMove extends Move {
    public MajorMove(Board board, Piece movedPiece, int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof MajorMove) && super.equals(other);
    }

    @Override
    public String toString() {
        return getMovedPiece().getType().toString() + disambiguationSolver() + BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
