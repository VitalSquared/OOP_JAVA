package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.communication.ConnectionStatus;
import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.controller.CommandStatus;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

import java.util.Arrays;

public final class BackCommand extends Command {
    private final SceneState[] allowed = new SceneState[] { SceneState.NEW_GAME_MENU, SceneState.CONNECTION_MENU, SceneState.RESULTS_MENU, SceneState.HIGH_SCORES_MENU, SceneState.ABOUT_MENU };

    public BackCommand(Scene scene) {
        super(scene);
    }

    @Override
    public CommandStatus execute(String[] args) {
        if (!Arrays.asList(allowed).contains(getScene().getSceneState())) return CommandStatus.INVALID_MENU;
        if (getScene().getSceneState() == SceneState.CONNECTION_MENU) {
            NetworkEntity networkEntity = (NetworkEntity) getScene().getActiveGame();
            if (networkEntity.connected() != ConnectionStatus.FAILED) return CommandStatus.INVALID_MENU;
        }
        if (args.length != 0) return CommandStatus.WRONG_NUMBER_OF_ARGUMENTS;
        getScene().setSceneState(getScene().getSceneState().getPrevScene());
        return CommandStatus.NORMAL;
    }
}
