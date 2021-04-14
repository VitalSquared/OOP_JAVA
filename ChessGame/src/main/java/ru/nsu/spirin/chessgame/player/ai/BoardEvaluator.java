package ru.nsu.spirin.chessgame.player.ai;

import ru.nsu.spirin.chessgame.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);
}
