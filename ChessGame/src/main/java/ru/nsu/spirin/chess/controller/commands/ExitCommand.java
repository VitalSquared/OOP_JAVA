package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public class ExitCommand extends Command {
    public ExitCommand(final Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(final String[] args, final boolean privileged) {
        if (getScene().getSceneState() != SceneState.MAIN_MENU || args.length != 0) {
            return false;
        }
        getScene().destroyScene();
        return true;
    }
}
