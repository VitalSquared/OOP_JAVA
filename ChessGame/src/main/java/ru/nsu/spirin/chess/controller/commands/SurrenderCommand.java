package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public class SurrenderCommand extends Command {
    public SurrenderCommand(final Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(final String[] args, final boolean privileged) {
        if (args.length != 0 || getScene().getSceneState() != SceneState.BOARD_MENU) {
            return false;
        }
        getScene().getBoard().getCurrentPlayer().surrender();
        return true;
    }
}
