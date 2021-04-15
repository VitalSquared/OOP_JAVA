package ru.nsu.spirin.chessgame.player;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.castle.KingSideCastleMove;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.board.tile.Tile;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves, final boolean isAI) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves, isAI);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !isInCheck()) {
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(5, opponentLegals).isEmpty() && calculateAttacksOnTile(6, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            if (!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (calculateAttacksOnTile(2, opponentLegals).isEmpty() && calculateAttacksOnTile(3, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 2, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
