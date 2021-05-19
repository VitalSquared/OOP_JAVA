package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardBuilder;
import ru.nsu.spirin.chess.model.pieces.Piece;

public final class PawnEnPassantAttackMove extends PawnAttackMove {
    public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof PawnEnPassantAttackMove) && super.equals(other);
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        for (Piece piece : this.getBoard().getAllPieces()) {
            if (!this.getMovedPiece().equals(piece)) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(this.getMovedPiece().movePiece(this));
        builder.setMoveMaker(this.getBoard().getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
}
