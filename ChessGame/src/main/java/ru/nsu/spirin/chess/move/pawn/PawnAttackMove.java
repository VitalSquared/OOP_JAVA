package ru.nsu.spirin.chess.move.pawn;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.move.attack.AttackMove;
import ru.nsu.spirin.chess.pieces.Piece;

public class PawnAttackMove extends AttackMove {
    public PawnAttackMove(final Board board, final Piece movePiece, final int destinationCoordinate, final Piece attackedPiece) {
        super(board, movePiece, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnAttackMove && super.equals(other);
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.getMovedPiece().getPiecePosition()).charAt(0) + "x" + BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
