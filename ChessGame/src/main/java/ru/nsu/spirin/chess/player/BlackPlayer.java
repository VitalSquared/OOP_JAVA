package ru.nsu.spirin.chess.player;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.castle.KingSideCastleMove;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.board.tile.Tile;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class BlackPlayer extends Player {
    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves, final boolean isAI, final String playerName) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves, isAI, playerName);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.getBoard().getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.getBoard().getWhitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.getPlayerKing().isFirstMove() && !isInCheck()) {
            if (!this.getBoard().getTile(5).isTileOccupied() && !this.getBoard().getTile(6).isTileOccupied()) {
                final Tile rookTile = this.getBoard().getTile(7);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(5, opponentLegals).isEmpty() && calculateAttacksOnTile(6, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.getBoard(), this.getPlayerKing(), 6, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            if (!this.getBoard().getTile(1).isTileOccupied() && !this.getBoard().getTile(2).isTileOccupied() && !this.getBoard().getTile(3).isTileOccupied()) {
                final Tile rookTile = this.getBoard().getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(2, opponentLegals).isEmpty() && calculateAttacksOnTile(3, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.getBoard(), this.getPlayerKing(), 2, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
