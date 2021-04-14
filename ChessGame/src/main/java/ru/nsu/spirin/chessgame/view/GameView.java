package ru.nsu.spirin.chessgame.view;

import ru.nsu.spirin.chessgame.board.Board;

public interface GameView {
    void render(Board board);
    void close();

    void show();
}
