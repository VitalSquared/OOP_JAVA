package ru.nsu.spirin.chessgame.player.ai;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.Move;

public interface MoveStrategy {
    Move execute(Board board);
}
