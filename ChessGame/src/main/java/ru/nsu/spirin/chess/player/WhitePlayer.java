package ru.nsu.spirin.chess.player;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.KingSideCastleMove;
import ru.nsu.spirin.chess.move.QueenSideCastleMove;
import ru.nsu.spirin.chess.board.tile.Tile;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class WhitePlayer extends Player {
    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
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
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        if (this.noCastleOpportunities()) {
            return Collections.emptyList();
        }
        List<Move> kingCastles = new ArrayList<>();
        if (this.getPlayerKing().isFirstMove() && !isInCheck()) {
            if (!this.getBoard().getTile(61).isTileOccupied() && !this.getBoard().getTile(62).isTileOccupied()) {
                Tile rookTile = this.getBoard().getTile(63);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(61, opponentLegals).isEmpty() && calculateAttacksOnTile(62, opponentLegals).isEmpty() && rookTile.getPiece().getType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.getBoard(), this.getPlayerKing(), 62, (Rook) rookTile.getPiece(), 61));
                    }
                }
            }
            if (!this.getBoard().getTile(59).isTileOccupied() && !this.getBoard().getTile(58).isTileOccupied() && !this.getBoard().getTile(57).isTileOccupied()) {
                Tile rookTile = this.getBoard().getTile(56);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(59, opponentLegals).isEmpty() && calculateAttacksOnTile(58, opponentLegals).isEmpty() && rookTile.getPiece().getType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(this.getBoard(), this.getPlayerKing(), 58, (Rook) rookTile.getPiece(), 59));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
