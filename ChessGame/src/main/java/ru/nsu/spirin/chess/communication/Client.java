package ru.nsu.spirin.chess.communication;

import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveTransition;

public final class Client extends GameEntity {
    private String opponentName;

    public Client() {

    }

    @Override
    public String getOpponentName() {
        return this.opponentName;
    }

    @Override
    public void makeMove(Move move, MoveTransition transition) {

    }
}
