package ru.nsu.spirin.chess.model.ai;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.move.Move;

public interface MoveStrategy {
    Move execute(Board board);
}
