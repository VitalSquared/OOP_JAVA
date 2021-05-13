package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.game.NetworkEntity;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.controller.CommandStatus;
import ru.nsu.spirin.chess.player.Player;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public final class ProceedToResultsCommand extends Command {
    public ProceedToResultsCommand(Scene scene) {
        super(scene);
    }

    @Override
    public CommandStatus execute(String[] args) {
        if (getScene().getSceneState() != SceneState.BOARD_MENU) return CommandStatus.INVALID_MENU;
        if (args.length != 0) return CommandStatus.WRONG_NUMBER_OF_ARGUMENTS;

        calculateScore();
        getScene().setSceneState(SceneState.RESULTS_MENU);
        try {
            ((NetworkEntity) getScene().getActiveGame()).closeConnection();
        }
        catch (Exception ignored) {
        }
        return CommandStatus.NORMAL;
    }

    private void calculateScore() {
        Player player = getScene().getActiveGame().getPlayerAlliance().choosePlayer(getScene().getActiveGame().getBoard().getWhitePlayer(), getScene().getActiveGame().getBoard().getBlackPlayer());
        if (getScene().getActiveGame().getBoard().getWhitePlayer().isInStaleMate() || getScene().getActiveGame().getBoard().getBlackPlayer().isInStaleMate()) {
            getScene().getActiveGame().addScoreText("DRAW", 500);
        }
        else if (player.isInCheckMate() || player.isResigned()) {
            getScene().getActiveGame().addScoreText("Lost", -500);
        }
        else {
            getScene().getActiveGame().addScoreText("Victory", 1000);
        }
    }
}
