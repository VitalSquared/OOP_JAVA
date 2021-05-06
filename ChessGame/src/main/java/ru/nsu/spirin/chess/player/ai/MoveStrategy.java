package ru.nsu.spirin.chess.player.ai;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.Move;

public interface MoveStrategy {
    Move execute(Board board);
}
