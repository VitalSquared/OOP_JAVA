package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.model.match.server.ConnectionStatus;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.controller.CommandStatus;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.model.scene.SceneState;

public final class ReadyCommand extends Command {
    public ReadyCommand(Scene scene) {
        super(scene);
    }

    @Override
    public CommandStatus execute(String[] args) {
        if (getScene().getSceneState() != SceneState.CONNECTION_MENU) return CommandStatus.INVALID_MENU;
        MatchEntity matchEntity = getScene().getActiveGame();
        if (matchEntity.connected() != ConnectionStatus.CONNECTED) return CommandStatus.INVALID_MENU;
        if (args.length != 0) return CommandStatus.WRONG_NUMBER_OF_ARGUMENTS;
        if (matchEntity.getPlayerAlliance() == null ||
            matchEntity.getPlayerAlliance() == matchEntity.getOpponentAlliance()) return CommandStatus.INVALID_TEAM;
        matchEntity.setPlayerReady(!matchEntity.isPlayerReady());
        return CommandStatus.NORMAL;
    }
}
