package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public final class CancelCommand extends Command {
    public CancelCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        NetworkEntity networkEntity = (NetworkEntity) getScene().getActiveGame();
        networkEntity.closeConnection();
        getScene().setSceneState(SceneState.NEW_GAME_MENU);
        return true;
    }
}
