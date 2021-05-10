package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.communication.NetworkEntity;
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
        try {
            ((NetworkEntity) getScene().getActiveGame()).closeConnection();
        }
        catch (Exception ignored) {
        }
        return true;
    }

    private void calculateScore() {
        Player player = getScene().getActiveGame().getPlayerAlliance().choosePlayer(getScene().getActiveGame().getBoard().getWhitePlayer(), getScene().getActiveGame().getBoard().getBlackPlayer());
        if (player.isInStaleMate() || player.isInCheckMate() || player.isResigned()) {
            getScene().getActiveGame().addScoreText("Lost", -500);
        }
        else {
            getScene().getActiveGame().addScoreText("Victory", 1000);
        }
    }
}
