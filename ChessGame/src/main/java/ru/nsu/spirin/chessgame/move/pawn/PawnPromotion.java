package ru.nsu.spirin.chessgame.move.pawn;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardBuilder;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.pieces.Pawn;
import ru.nsu.spirin.chessgame.pieces.Piece;

public final class PawnPromotion extends Move {
    private final Move decoratedMove;
    private final Pawn promotedPawn;

    public PawnPromotion(final Move decoratedMove) {
        super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
        this.decoratedMove = decoratedMove;
        this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
    }

    @Override
    public Board execute() {
        final Board pawnMovedBoard = this.decoratedMove.execute();
        final BoardBuilder boardBuilder = new BoardBuilder();
        for (final Piece piece : pawnMovedBoard.getCurrentPlayer().getActivePieces()) {
            if (!this.promotedPawn.equals(piece)) {
                boardBuilder.setPiece(piece);
            }
        }
        for (final Piece piece : pawnMovedBoard.getCurrentPlayer().getOpponent().getActivePieces()) {
            boardBuilder.setPiece(piece);
        }
        boardBuilder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
        boardBuilder.setMoveMaker(pawnMovedBoard.getCurrentPlayer().getAlliance());

        boardBuilder.copyPlayerInfo(this.getBoard().getWhitePlayer(), this.getBoard().getBlackPlayer());

        return boardBuilder.build();
    }

    @Override
    public boolean isAttack() {
        return this.decoratedMove.isAttack();
    }

    @Override
    public Piece getAttackedPiece() {
        return this.decoratedMove.getAttackedPiece();
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(getDestinationCoordinate()) + this.promotedPawn.getPromotionPiece().getPieceType().toString().charAt(0);
    }

    @Override
    public int hashCode() {
        return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnPromotion && super.equals(other);
    }
}
