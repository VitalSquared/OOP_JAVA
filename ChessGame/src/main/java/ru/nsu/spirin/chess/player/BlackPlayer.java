package ru.nsu.spirin.chess.player;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.KingSideCastleMove;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.board.tile.Tile;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class BlackPlayer extends Player {
    public BlackPlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves, boolean isAI, String playerName) {
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
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves) {
        if (!this.hasCastleOpportunities()) {
            return Collections.emptyList();
        }
        List<Move> kingCastles = new ArrayList<>();
        if (this.getPlayerKing().isFirstMove() && !isInCheck()) {
            if (!this.getBoard().getTile(5).isTileOccupied() && !this.getBoard().getTile(6).isTileOccupied()) {
                Tile rookTile = this.getBoard().getTile(7);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(5, opponentLegalMoves).isEmpty() && calculateAttacksOnTile(6, opponentLegalMoves).isEmpty() && rookTile.getPiece().getType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.getBoard(), this.getPlayerKing(), 6, (Rook) rookTile.getPiece(), rookTile.getCoordinate(), 5));
                    }
                }
            }
            if (!this.getBoard().getTile(1).isTileOccupied() && !this.getBoard().getTile(2).isTileOccupied() && !this.getBoard().getTile(3).isTileOccupied()) {
                Tile rookTile = this.getBoard().getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(2, opponentLegalMoves).isEmpty() && calculateAttacksOnTile(3, opponentLegalMoves).isEmpty() && rookTile.getPiece().getType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.getBoard(), this.getPlayerKing(), 2, (Rook) rookTile.getPiece(), rookTile.getCoordinate(), 3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
