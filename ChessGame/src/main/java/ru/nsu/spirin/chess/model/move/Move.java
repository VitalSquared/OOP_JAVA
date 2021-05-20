package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardBuilder;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.pieces.Piece;

import java.io.Serializable;
import java.util.Objects;

public abstract class Move implements Serializable {
    private final Board board;
    private final Piece movedPiece;
    private final int   destinationCoordinate;

    protected Move(Board board, Piece movedPiece, int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(1, this.destinationCoordinate, this.movedPiece, this.movedPiece.getCoordinate());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Move)) return false;
        Move otherMove = (Move) other;
        return this.getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
               this.getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
               this.getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public Board getBoard() {
        return this.board;
    }

    public int getCurrentCoordinate() {
        return this.movedPiece.getCoordinate();
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        for (Piece piece : this.getBoard().getAllPieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    protected String disambiguationSolver() {
        char curFile = BoardUtils.getPositionAtCoordinate(this.movedPiece.getCoordinate()).charAt(0);
        char curRank = BoardUtils.getPositionAtCoordinate(this.movedPiece.getCoordinate()).charAt(1);

        boolean anySimilarFiles = false;
        boolean anySimilarRanks = false;
        for (Move move : this.board.getCurrentPlayer().getLegalMoves()) {
            if (move.getDestinationCoordinate() == this.destinationCoordinate && !this.equals(move) && this.movedPiece.getType().equals(move.getMovedPiece().getType())) {
                char otherFile = BoardUtils.getPositionAtCoordinate(move.getCurrentCoordinate()).charAt(0);
                char otherRank = BoardUtils.getPositionAtCoordinate(move.getCurrentCoordinate()).charAt(1);
                if (curFile == otherFile) anySimilarFiles = true;
                if (curRank == otherRank) anySimilarRanks = true;
            }
        }
        if (anySimilarFiles && anySimilarRanks) return curFile + "" + curRank;
        if (anySimilarFiles) return curRank + "";
        if (anySimilarRanks) return curFile + "";
        return "";
    }
}
