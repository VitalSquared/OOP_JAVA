package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.pieces.Piece;

public class PawnAttackMove extends AttackMove {
    public PawnAttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof PawnAttackMove) && super.equals(other);
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.getMovedPiece().getCoordinate()).charAt(0) + "x" + BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
