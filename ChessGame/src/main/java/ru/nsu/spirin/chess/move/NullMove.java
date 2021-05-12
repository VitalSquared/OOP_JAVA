package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;

public final class NullMove extends Move {
    public NullMove() {
        super(null,null, 65);
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
