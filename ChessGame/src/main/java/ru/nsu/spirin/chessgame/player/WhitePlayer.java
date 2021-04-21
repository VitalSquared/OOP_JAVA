package ru.nsu.spirin.chessgame.player;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.move.castle.KingSideCastleMove;
import ru.nsu.spirin.chessgame.move.castle.QueenSideCastleMove;
import ru.nsu.spirin.chessgame.board.tile.Tile;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class WhitePlayer extends Player {
    public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves, final boolean isAI, final String playerName) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves, isAI, playerName);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.getBoard().getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.getBoard().getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.getPlayerKing().isFirstMove() && !isInCheck()) {
            if (!this.getBoard().getTile(61).isTileOccupied() && !this.getBoard().getTile(62).isTileOccupied()) {
                final Tile rookTile = this.getBoard().getTile(63);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(61, opponentLegals).isEmpty() && calculateAttacksOnTile(62, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.getBoard(), this.getPlayerKing(), 62, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            if (!this.getBoard().getTile(59).isTileOccupied() && !this.getBoard().getTile(58).isTileOccupied() && !this.getBoard().getTile(57).isTileOccupied()) {
                final Tile rookTile = this.getBoard().getTile(56);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(59, opponentLegals).isEmpty() && calculateAttacksOnTile(58, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(this.getBoard(), this.getPlayerKing(), 58, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
