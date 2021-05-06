package ru.nsu.spirin.chess.player.ai;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveTransition;

import java.util.Collection;
import java.util.Optional;

public class MiniMax implements MoveStrategy {
    private final BoardEvaluator boardEvaluator;
    private final int            searchDepth;

    public MiniMax(final int searchDepth) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString() {
        return "Minimax";
    }

    @Override
    public Move execute(Board board) {

        //final long startTime = System.currentTimeMillis();

        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.getCurrentPlayer().getAlliance() + " THINKING with depth = " + this.searchDepth);

        //int numMoves = board.getCurrentPlayer().getLegalMoves().size();

        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {

            final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currentValue = board.getCurrentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getTransitionBoard(), this.searchDepth - 1) :
                        max(moveTransition.getTransitionBoard(), this.searchDepth - 1);

                if (board.getCurrentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                }
                else if (board.getCurrentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }

        //final long executionTime = System.currentTimeMillis() - startTime;

        if (bestMove == null) {
            Collection<Move> legalMoves = board.getCurrentPlayer().getLegalMoves();
            Optional<Move> randomMove = legalMoves.stream().skip((int) (legalMoves.size() * Math.random())).findFirst();
            bestMove = randomMove.orElse(null);
        }

        return bestMove;
    }

    private boolean isEndGameScenario(final Board board) {
        return board.getCurrentPlayer().isInCheckMate() || board.getCurrentPlayer().isInStaleMate();
    }

    private int min(final Board board, final int depth) {
        if (depth == 0 || isEndGameScenario(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }
            }
        }

        return lowestSeenValue;
    }

    private int max(final Board board, final int depth) {
        if (depth == 0 || isEndGameScenario(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }

        int highestSeenValue = Integer.MIN_VALUE;
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = min(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }

        return highestSeenValue;
    }
}