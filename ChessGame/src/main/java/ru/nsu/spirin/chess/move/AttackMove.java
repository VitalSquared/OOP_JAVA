package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.pieces.Piece;

public abstract class AttackMove extends Move {
    private final Piece attackedPiece;

    public AttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate);
        this.attackedPiece = attackedPiece;
    }

    @Override
    public int hashCode() {
        return this.attackedPiece.hashCode() + super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return false;
        if (!(other instanceof AttackMove)) return false;
        AttackMove otherAttackMove = (AttackMove) other;
        return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
    }

    @Override
    public boolean isAttack() {
        return true;
    }

    @Override
    public Piece getAttackedPiece() {
        return attackedPiece;
    }
}
