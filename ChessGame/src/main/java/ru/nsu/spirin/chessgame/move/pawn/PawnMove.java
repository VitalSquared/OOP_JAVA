package ru.nsu.spirin.chessgame.move.pawn;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.pieces.Piece;

public final class PawnMove extends Move {

    public PawnMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
        super(board, movePiece, destinationCoordinate);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnMove && super.equals(other);
    }
}
