package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.pieces.Piece;

public final class MajorAttackMove extends AttackMove {
    public MajorAttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof MajorAttackMove) && super.equals(other);
    }

    @Override
    public String toString() {
        return getMovedPiece().getType().toString() + disambiguationSolver() + "x" + BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
