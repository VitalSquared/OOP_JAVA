package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardBuilder;
import ru.nsu.spirin.chess.model.pieces.Piece;
import ru.nsu.spirin.chess.model.pieces.Rook;

import java.util.Objects;

public abstract class CastleMove extends Move {
    private final Rook castleRook;
    private final int  castleRookDestination;

    public CastleMove(Board board, Piece movePiece, int destinationCoordinate, Rook castleRook, int castleRookDestination) {
        super(board, movePiece, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookDestination = castleRookDestination;
    }

    public Rook getCastleRook() {
        return this.castleRook;
    }

    @Override
    public boolean isCastlingMove() {
        return true;
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        for (Piece piece : this.getBoard().getAllPieces()) {
            if (!this.getMovedPiece().equals(piece) && !this.castleRook.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(this.getMovedPiece().movePiece(this));
        builder.setPiece(new Rook(this.castleRook.getAlliance(), this.castleRookDestination));
        builder.setMoveMaker(this.getBoard().getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.castleRook, this.castleRookDestination);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CastleMove)) return false;
        CastleMove otherCastleMove = (CastleMove) other;
        return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook()) &&
               this.castleRookDestination == otherCastleMove.castleRookDestination;
    }
}
