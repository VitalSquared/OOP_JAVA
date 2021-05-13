package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.pieces.Piece;

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
