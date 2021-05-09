package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.properties.SettingsFile;
import ru.nsu.spirin.chess.scene.Scene;

public final class StartCommand extends Command {
    public StartCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        boolean isWhitePlayer= args[0].equalsIgnoreCase("white");

        try {
            SettingsFile.saveSetting("LAST_USED_NAME", args[1]);
        }
        catch (Exception ignored) {}

        getScene().startLocalGame(args[1], isWhitePlayer ? Alliance.WHITE : Alliance.BLACK);
        return true;
    }
}
