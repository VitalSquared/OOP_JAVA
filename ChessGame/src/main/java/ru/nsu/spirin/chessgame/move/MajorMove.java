package ru.nsu.spirin.chessgame.move;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.pieces.Piece;

public final class MajorMove extends Move {

    public MajorMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
        super(board, movePiece, destinationCoordinate);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof ru.nsu.spirin.chessgame.move.MajorMove && super.equals(other);
    }

    @Override
    public String toString() {
        return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
