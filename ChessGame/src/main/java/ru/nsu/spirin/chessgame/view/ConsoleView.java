package ru.nsu.spirin.chessgame.view;

import ru.nsu.spirin.chessgame.board.Board;

public class ConsoleView implements GameView {

    public ConsoleView() {

    }

    @Override
    public void render(Board board) {
        System.out.println(board.toString());
    }

    @Override
    public void close() {}
}
