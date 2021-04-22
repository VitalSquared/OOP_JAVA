package ru.nsu.spirin.chessgame.controller.commands;

import ru.nsu.spirin.chessgame.controller.Command;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.scene.SceneState;

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
