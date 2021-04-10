package ru.nsu.spirin.chessgame.move.pawn;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardBuilder;
import ru.nsu.spirin.chessgame.pieces.Piece;

public final class PawnEnPassantAttackMove extends PawnAttackMove {

    public PawnEnPassantAttackMove(final Board board, final Piece movePiece, final int destinationCoordinate, final Piece attackedPiece) {
        super(board, movePiece, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
    }

    @Override
    public Board execute() {
        final BoardBuilder boardBuilder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                boardBuilder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            if (!piece.equals(this.getAttackedPiece())) {
                boardBuilder.setPiece(piece);
            }
        }
        boardBuilder.setPiece(this.movedPiece.movePiece(this));
        boardBuilder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return boardBuilder.build();
    }
}
