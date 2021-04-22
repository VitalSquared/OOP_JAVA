package ru.nsu.spirin.chessgame.player.ai;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.player.Player;

public class StandardBoardEvaluator implements BoardEvaluator {
    private static final int CHECK_BONUS      = 50;
    private static final int CHECK_MATE_BONUS = 1000;
    private static final int DEPTH_BONUS      = 100;
    private static final int CASTLE_BONUS     = 60;
    private static final int PROMOTION_BONUS  = 60;

    @Override
    public int evaluate(final Board board, final int depth) {
        return scorePlayer(board, board.getWhitePlayer(), depth) - scorePlayer(board, board.getBlackPlayer(), depth);
    }

    private int scorePlayer(final Board board, final Player player, final int depth) {
        return pieceValue(player) + mobility(player) + check(player) + checkmate(player, depth) + castled(player) + promotions(player);
    }

    private int castled(final Player player) {
        return player.isCastled() ?
                CASTLE_BONUS :
                0;
    }

    private int checkmate(final Player player, int depth) {
        return player.getOpponent().isInCheckMate() ?
                CHECK_MATE_BONUS * depthBonus(depth) :
                0;
    }

    private static int depthBonus(final int depth) {
        return depth == 0 ?
                1 :
                DEPTH_BONUS * depth;
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ?
                CHECK_BONUS :
                0;
    }

    private static int mobility(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int promotions(final Player player) {
        return PROMOTION_BONUS * player.getPromotedPawns();
    }

    private int pieceValue(Player player) {
        int pieceValueScore = 0;
        for (final Piece piece : player.getActivePieces()) {
            pieceValueScore += piece.getPieceType().getPieceValue();
        }
        return pieceValueScore;
    }
}
