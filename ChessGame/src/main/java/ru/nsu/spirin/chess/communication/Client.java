package ru.nsu.spirin.chess.communication;

public final class Client extends GameEntity {
    private String opponentName;

    public Client() {

    }

    @Override
    public String getOpponentName() {
        return this.opponentName;
    }
}
