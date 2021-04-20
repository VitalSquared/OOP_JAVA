package ru.nsu.spirin.chessgame.move.castle;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardBuilder;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.pieces.Rook;

import java.util.Objects;

public abstract class CastleMove extends Move {
    protected final Rook castleRook;
    protected final int  castleRookStart;
    protected final int  castleRookDestination;

    public CastleMove(final Board board, final Piece movePiece, final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
        super(board, movePiece, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
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
        final BoardBuilder boardBuilder = new BoardBuilder();
        for (final Piece piece : this.getBoard().getCurrentPlayer().getActivePieces()) {
            if (!this.getMovedPiece().equals(piece) && !this.castleRook.equals(piece)) {
                boardBuilder.setPiece(piece);
            }
        }
        for (final Piece piece : this.getBoard().getCurrentPlayer().getOpponent().getActivePieces()) {
            boardBuilder.setPiece(piece);
        }
        boardBuilder.setPiece(this.getMovedPiece().movePiece(this));
        boardBuilder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookDestination));
        boardBuilder.setMoveMaker(this.getBoard().getCurrentPlayer().getOpponent().getAlliance());

        boardBuilder.setWhiteAI(this.getBoard().getWhitePlayer().isAI());
        boardBuilder.setBlackAI(this.getBoard().getBlackPlayer().isAI());

        return boardBuilder.build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.castleRook, this.castleRookDestination);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CastleMove)) {
            return false;
        }
        final CastleMove otherCastleMove = (CastleMove) other;
        return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
    }
}
