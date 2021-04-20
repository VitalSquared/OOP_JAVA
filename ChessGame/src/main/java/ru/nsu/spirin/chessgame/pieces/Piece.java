package ru.nsu.spirin.chessgame.pieces;

import ru.nsu.spirin.chessgame.player.Alliance;
import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.Move;

import java.util.Collection;
import java.util.Objects;

public abstract class Piece {
    private final PieceType pieceType;
    private final int       piecePosition;
    private final Alliance  pieceAlliance;
    private final boolean   isFirstMove;
    private final int       hashCode;

    protected Piece(final PieceType pieceType, final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = isFirstMove;
        this.hashCode = Objects.hash(pieceType, pieceAlliance, piecePosition, isFirstMove);
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(final Move move);

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && pieceAlliance == ((Piece) other).getPieceAlliance() && isFirstMove == otherPiece.isFirstMove;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }
}
