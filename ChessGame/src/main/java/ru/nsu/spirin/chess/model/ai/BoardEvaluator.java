package ru.nsu.spirin.chess.model.ai;

import ru.nsu.spirin.chess.model.board.Board;

public interface BoardEvaluator {
    int evaluate(Board board, int depth);
}
