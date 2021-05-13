package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardBuilder;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.pieces.Pawn;
import ru.nsu.spirin.chess.pieces.Piece;

import java.util.Objects;

public final class PawnPromotion extends Move {
    private final Move decoratedMove;
    private final Pawn promotedPawn;

    public PawnPromotion(Move decoratedMove) {
        super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
        this.decoratedMove = decoratedMove;
        this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
    }

    @Override
    public Board execute() {
        Board pawnMovedBoard = this.decoratedMove.execute();
        BoardBuilder builder = new BoardBuilder();
        for (Piece piece : pawnMovedBoard.getAllPieces()) {
            if (!this.promotedPawn.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
        builder.setMoveMaker(pawnMovedBoard.getCurrentPlayer().getAlliance());
        return builder.build();
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
        return BoardUtils.getPositionAtCoordinate(getDestinationCoordinate()) + "=" + this.promotedPawn.getPromotionPiece().getType().toString().charAt(0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(decoratedMove, promotedPawn);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof PawnPromotion) && super.equals(other);
    }
}
