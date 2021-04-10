package ru.nsu.spirin.chessgame.move.attack;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.pieces.Piece;

public class MajorAttackMove extends AttackMove {

    public MajorAttackMove(final Board board, final Piece pieceMoved, final int destinationCoordinate, final Piece pieceAttacked) {
        super(board, pieceMoved, destinationCoordinate, pieceAttacked);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof MajorAttackMove && super.equals(other);
    }

    @Override
    public String toString() {
        return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
