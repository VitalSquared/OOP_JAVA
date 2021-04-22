package ru.nsu.spirin.chessgame.controller.commands;

import ru.nsu.spirin.chessgame.controller.Command;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.scene.SceneState;

public class SurrenderCommand extends Command {
    public SurrenderCommand(final Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(final String[] args, final boolean privileged) {
        if (args.length != 0 || getScene().getSceneState() != SceneState.GAME) {
            return false;
        }
        getScene().getBoard().getCurrentPlayer().surrender();
        return true;
    }
}
