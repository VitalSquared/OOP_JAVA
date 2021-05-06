package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;

public final class BackCommand extends Command {
    public BackCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        getScene().setSceneState(getScene().getSceneState().getPrevScene());
        return false;
    }
}
