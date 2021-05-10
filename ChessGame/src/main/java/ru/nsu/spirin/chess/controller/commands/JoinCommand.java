package ru.nsu.spirin.chess.controller.commands;

import com.google.common.net.InetAddresses;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.properties.SettingsFile;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

import java.io.IOException;

public final class JoinCommand extends Command {
    public JoinCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        if (args.length != 3 && getScene().getSceneState() != SceneState.NEW_GAME_MENU) {
            return false;
        }

        if (!InetAddresses.isInetAddress(args[0]) && !args[0].equals("localhost")) return false;

        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 1 || port > 65535) return false;

            getScene().joinServerGame(args[0], port, args[2]);

            try {
                SettingsFile.saveSetting("LAST_USED_NAME", args[2]);
            }
            catch (Exception ignored) {}
        }
        catch (NumberFormatException | IOException ignored) {
            return false;
        }

        return true;
    }
}
