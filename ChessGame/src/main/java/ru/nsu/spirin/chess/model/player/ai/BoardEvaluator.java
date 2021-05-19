package ru.nsu.spirin.chess.model.player.ai;

import ru.nsu.spirin.chess.model.board.Board;

public interface BoardEvaluator {
    int evaluate(Board board, int depth);
}
