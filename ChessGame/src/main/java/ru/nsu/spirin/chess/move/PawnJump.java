package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.board.BoardBuilder;
import ru.nsu.spirin.chess.pieces.Pawn;
import ru.nsu.spirin.chess.pieces.Piece;

public final class PawnJump extends Move {
    public PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate);
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        for (final Piece piece : this.getBoard().getAllPieces()) {
            if (!this.getMovedPiece().equals(piece)) {
                builder.setPiece(piece);
            }
        }
        Pawn movedPawn = (Pawn) this.getMovedPiece().movePiece(this);
        builder.setPiece(movedPawn);
        builder.setEnPassantPawn(movedPawn);
        builder.setMoveMaker(this.getBoard().getCurrentPlayer().getOpponent().getAlliance());
        builder.copyPlayerInfo(this.getBoard().getWhitePlayer(), this.getBoard().getBlackPlayer());
        return builder.build();
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
