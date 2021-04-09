package ru.nsu.spirin.chessgame.player;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.Move;

import java.util.concurrent.Future;

public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
