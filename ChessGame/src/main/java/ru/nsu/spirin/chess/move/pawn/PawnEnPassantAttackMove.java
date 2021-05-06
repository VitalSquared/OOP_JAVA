package ru.nsu.spirin.chess.move.pawn;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardBuilder;
import ru.nsu.spirin.chess.pieces.Piece;

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
        for (final Piece piece : this.getBoard().getCurrentPlayer().getActivePieces()) {
            if (!this.getMovedPiece().equals(piece)) {
                boardBuilder.setPiece(piece);
            }
        }
        for (final Piece piece : this.getBoard().getCurrentPlayer().getOpponent().getActivePieces()) {
            if (!piece.equals(this.getAttackedPiece())) {
                boardBuilder.setPiece(piece);
            }
        }
        boardBuilder.setPiece(this.getMovedPiece().movePiece(this));
        boardBuilder.setMoveMaker(this.getBoard().getCurrentPlayer().getOpponent().getAlliance());

        boardBuilder.copyPlayerInfo(this.getBoard().getWhitePlayer(), this.getBoard().getBlackPlayer());

        return boardBuilder.build();
    }
}