package ru.nsu.spirin.chess.pieces;

import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.Move;

import java.util.Collection;
import java.util.Objects;

public abstract class Piece {
    private final PieceType type;
    private final int       coordinate;
    private final Alliance  alliance;
    private final boolean   isFirstMove;
    private final int       hashCode;

    protected Piece(PieceType type, Alliance alliance, int coordinate, boolean isFirstMove) {
        this.type = type;
        this.coordinate = coordinate;
        this.alliance = alliance;
        this.isFirstMove = isFirstMove;
        this.hashCode = Objects.hash(type, alliance, coordinate, isFirstMove);
    }

    public abstract Collection<Move> calculateLegalMoves(Board board);

    public abstract Piece movePiece(Move move);

    public PieceType getType() {
        return this.type;
    }

    public int getCoordinate() {
        return this.coordinate;
    }

    public Alliance getAlliance() {
        return this.alliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Piece)) return false;
        Piece otherPiece = (Piece) other;
        return this.coordinate == otherPiece.coordinate && this.type == otherPiece.type &&
               this.alliance == otherPiece.getAlliance() && this.isFirstMove == otherPiece.isFirstMove;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}
