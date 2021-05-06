package ru.nsu.spirin.chess.move.pawn;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.pieces.Piece;

public final class PawnMove extends Move {
    public PawnMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
        super(board, movePiece, destinationCoordinate);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnMove && super.equals(other);
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
