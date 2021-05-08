package ru.nsu.spirin.chess.communication;

public final class Local extends GameEntity {

    public Local() {

    }

    @Override
    public String getOpponentName() {
        return "AI";
    }
}
