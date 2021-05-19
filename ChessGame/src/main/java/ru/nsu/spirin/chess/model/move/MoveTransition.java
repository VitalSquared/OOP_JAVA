package ru.nsu.spirin.chess.model.move;

import ru.nsu.spirin.chess.model.board.Board;

import java.io.Serializable;

public final class MoveTransition implements Serializable {
    private final Board      transitionBoard;
    private final Move       move;
    private final MoveStatus moveStatus;

    public MoveTransition(Board transitionBoard, Move move, MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }

    public Move getMove() {
        return this.move;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
