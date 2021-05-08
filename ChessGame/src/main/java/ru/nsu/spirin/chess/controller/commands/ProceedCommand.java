package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.player.Player;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public final class ProceedCommand extends Command {
    public ProceedCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        calculateScore();
        getScene().setSceneState(SceneState.RESULTS_MENU);
        return true;
    }

    private void calculateScore() {
        Player player = getScene().getPlayerTeam().choosePlayer(getScene().getBoard().getWhitePlayer(), getScene().getBoard().getBlackPlayer());
        if (player.isInStaleMate() || player.isInCheckMate() || player.isResigned()) {
            getScene().addScoreText("Lost", -500);
        }
        else {
            getScene().addScoreText("Victory", 1000);
        }
    }
}
