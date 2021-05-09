package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;

public final class ReadyCommand extends Command {
    public ReadyCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        NetworkEntity networkEntity = (NetworkEntity) getScene().getActiveGame();
        networkEntity.setPlayerReady(!networkEntity.isPlayerReady());
        return true;
    }
}
