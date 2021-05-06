package ru.nsu.spirin.chess.player.ai;

import ru.nsu.spirin.chess.board.Board;

public interface BoardEvaluator {
    int evaluate(Board board, int depth);
}
