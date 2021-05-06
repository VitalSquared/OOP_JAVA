package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.pieces.Piece;

public final class MajorMove extends Move {
    public MajorMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
        super(board, movePiece, destinationCoordinate);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof MajorMove && super.equals(other);
    }

    @Override
    public String toString() {
        return getMovedPiece().getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
