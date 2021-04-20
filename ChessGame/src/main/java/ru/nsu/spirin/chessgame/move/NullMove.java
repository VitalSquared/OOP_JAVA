package ru.nsu.spirin.chessgame.move;

import ru.nsu.spirin.chessgame.board.Board;

public final class NullMove extends Move {
    public NullMove() {
        super(null, 65);
    }

    @Override
    public Board execute() {
        throw new RuntimeException("Can't execute null move");
    }

    @Override
    public int getCurrentCoordinate() {
        return -1;
    }
}
