package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;

import java.io.Serializable;

public class MoveTransition implements Serializable {

    private final Board      transitionBoard;
    private final Move       move;
    private final MoveStatus moveStatus;

    public MoveTransition(Board transitionBoard, Move move, MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Move getMove() {
        return this.move;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }
}
