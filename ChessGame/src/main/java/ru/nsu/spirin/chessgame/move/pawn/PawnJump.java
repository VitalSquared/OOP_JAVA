package ru.nsu.spirin.chessgame.move.pawn;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.board.BoardBuilder;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.pieces.Pawn;
import ru.nsu.spirin.chessgame.pieces.Piece;

public final class PawnJump extends Move {

    public PawnJump(final Board board, final Piece movePiece, final int destinationCoordinate) {
        super(board, movePiece, destinationCoordinate);
    }

    @Override
    public Board execute() {
        final BoardBuilder boardBuilder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                boardBuilder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            boardBuilder.setPiece(piece);
        }
        final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
        boardBuilder.setPiece(movedPawn);
        boardBuilder.setEnPassantPawn(movedPawn);
        boardBuilder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());

        boardBuilder.setWhiteAI(this.board.getWhitePlayer().isAI());
        boardBuilder.setBlackAI(this.board.getBlackPlayer().isAI());

        return boardBuilder.build();
    }

    @Override
    public String toString() {
        return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
