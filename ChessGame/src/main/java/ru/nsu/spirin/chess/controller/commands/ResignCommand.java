package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public class ResignCommand extends Command {
    public ResignCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        if (args.length != 0 || getScene().getSceneState() != SceneState.BOARD_MENU) {
            return false;
        }
        getScene().getBoard().getCurrentPlayer().resign();
        return true;
    }
}
