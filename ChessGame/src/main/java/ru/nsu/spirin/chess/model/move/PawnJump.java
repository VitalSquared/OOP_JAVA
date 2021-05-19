package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.board.BoardBuilder;
import ru.nsu.spirin.chess.model.pieces.Pawn;
import ru.nsu.spirin.chess.model.pieces.Piece;

public final class PawnJump extends Move {
    public PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate);
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        for (Piece piece : this.getBoard().getAllPieces()) {
            if (!this.getMovedPiece().equals(piece)) {
                builder.setPiece(piece);
            }
        }
        Pawn movedPawn = (Pawn) this.getMovedPiece().movePiece(this);
        builder.setPiece(movedPawn);
        builder.setEnPassantPawn(movedPawn);
        builder.setMoveMaker(this.getBoard().getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
