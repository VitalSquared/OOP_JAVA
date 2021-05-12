package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardBuilder;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.player.Alliance;

public final class ResignMove extends Move {
    private final Alliance alliance;

    public ResignMove(Board board, Alliance alliance) {
        super(board, null, -1);
        this.alliance = alliance;
    }

    @Override
    public Board execute() {
        BoardBuilder builder = new BoardBuilder();
        for (Piece piece : this.getBoard().getAllPieces()) {
            builder.setPiece(piece);
        }
        builder.setPlayerResigned(alliance.choosePlayer(getBoard().getWhitePlayer(), getBoard().getBlackPlayer()));
        return builder.build();
    }

    @Override
    public String toString() {
        return "resign";
    }
}
