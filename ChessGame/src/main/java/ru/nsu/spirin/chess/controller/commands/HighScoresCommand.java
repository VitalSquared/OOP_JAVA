package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public class HighScoresCommand extends Command {
    public HighScoresCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute (String[] args, boolean privileged) {
        getScene().setSceneState(SceneState.HIGH_SCORES_MENU);
        return true;
    }
}
