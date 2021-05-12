package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.communication.ConnectionStatus;
import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.controller.CommandStatus;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public final class ReadyCommand extends Command {
    public ReadyCommand(Scene scene) {
        super(scene);
    }

    @Override
    public CommandStatus execute(String[] args) {
        if (getScene().getSceneState() != SceneState.CONNECTION_MENU) return CommandStatus.INVALID_MENU;
        NetworkEntity networkEntity = (NetworkEntity) getScene().getActiveGame();
        if (networkEntity.connected() != ConnectionStatus.CONNECTED) return CommandStatus.INVALID_MENU;
        if (args.length != 0) return CommandStatus.WRONG_NUMBER_OF_ARGUMENTS;
        networkEntity.setPlayerReady(!networkEntity.isPlayerReady());
        return CommandStatus.NORMAL;
    }
}
