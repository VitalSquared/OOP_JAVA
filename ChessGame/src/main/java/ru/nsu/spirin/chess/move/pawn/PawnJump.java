package ru.nsu.spirin.chess.move.pawn;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.board.BoardBuilder;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.pieces.Pawn;
import ru.nsu.spirin.chess.pieces.Piece;

public final class PawnJump extends Move {
    public PawnJump(final Board board, final Piece movePiece, final int destinationCoordinate) {
        super(board, movePiece, destinationCoordinate);
    }

    @Override
    public Board execute() {
        final BoardBuilder boardBuilder = new BoardBuilder();
        for (final Piece piece : this.getBoard().getCurrentPlayer().getActivePieces()) {
            if (!this.getMovedPiece().equals(piece)) {
                boardBuilder.setPiece(piece);
            }
        }
        for (final Piece piece : this.getBoard().getCurrentPlayer().getOpponent().getActivePieces()) {
            boardBuilder.setPiece(piece);
        }
        final Pawn movedPawn = (Pawn) this.getMovedPiece().movePiece(this);
        boardBuilder.setPiece(movedPawn);
        boardBuilder.setEnPassantPawn(movedPawn);
        boardBuilder.setMoveMaker(this.getBoard().getCurrentPlayer().getOpponent().getAlliance());

        boardBuilder.copyPlayerInfo(this.getBoard().getWhitePlayer(), this.getBoard().getBlackPlayer());

        return boardBuilder.build();
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.getDestinationCoordinate());
    }
}
