package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.game.ConnectionStatus;
import ru.nsu.spirin.chess.game.NetworkEntity;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.controller.CommandStatus;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public final class TeamSelectionCommand extends Command {
    public TeamSelectionCommand(Scene scene) {
        super(scene);
    }

    @Override
    public CommandStatus execute(String[] args) {
        if (getScene().getSceneState() != SceneState.CONNECTION_MENU) return CommandStatus.INVALID_MENU;
        NetworkEntity networkEntity = (NetworkEntity) getScene().getActiveGame();
        if (networkEntity.connected() != ConnectionStatus.CONNECTED) return CommandStatus.INVALID_MENU;
        if (args.length != 1) return CommandStatus.WRONG_NUMBER_OF_ARGUMENTS;
        Alliance alliance;
        switch (args[0].toUpperCase()) {
            case "WHITE" -> alliance = Alliance.WHITE;
            case "BLACK" -> alliance = Alliance.BLACK;
            case "NONE" -> alliance = null;
            default -> {
                return CommandStatus.INVALID_ARGUMENTS;
            }
        }
        networkEntity.setPlayerAlliance(alliance);
        return CommandStatus.NORMAL;
    }
}
