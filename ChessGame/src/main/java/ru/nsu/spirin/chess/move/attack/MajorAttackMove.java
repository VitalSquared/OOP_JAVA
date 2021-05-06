package ru.nsu.spirin.chess.move.attack;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.pieces.Piece;

public final class MajorAttackMove extends AttackMove {
    public MajorAttackMove(final Board board, final Piece pieceMoved, final int destinationCoordinate, final Piece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate, pieceAttacked);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof MajorAttackMove && super.equals(other);
    }

    @Override
    public String toString() {
        return getMovedPiece().getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
