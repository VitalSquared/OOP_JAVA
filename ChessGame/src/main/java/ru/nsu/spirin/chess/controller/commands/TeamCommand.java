package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.scene.Scene;

public final class TeamCommand extends Command {
    public TeamCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        if (args.length != 1) return false;
        NetworkEntity networkEntity = (NetworkEntity) getScene().getActiveGame();
        Alliance alliance;
        switch (args[0].toUpperCase()) {
            case "WHITE" -> alliance = Alliance.WHITE;
            case "BLACK" -> alliance = Alliance.BLACK;
            case "NONE" -> alliance = null;
            default -> {
                return false;
            }
        }
        networkEntity.setPlayerTeam(alliance);
        return true;
    }
}
