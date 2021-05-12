package ru.nsu.spirin.chess.controller.commands;

import com.google.common.net.InetAddresses;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.controller.CommandStatus;
import ru.nsu.spirin.chess.properties.SettingsFile;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

import java.io.IOException;

public final class JoinCommand extends Command {
    public JoinCommand(Scene scene) {
        super(scene);
    }

    @Override
    public CommandStatus execute(String[] args) {
        if (getScene().getSceneState() != SceneState.NEW_GAME_MENU) return CommandStatus.INVALID_MENU;
        if (args.length != 3) return CommandStatus.WRONG_NUMBER_OF_ARGUMENTS;

        //TODO: Change IP address validation
        if (!InetAddresses.isInetAddress(args[0]) && !args[0].equals("localhost")) return CommandStatus.INVALID_IP;

        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 1 || port > 65535) return CommandStatus.INVALID_PORT;

            getScene().joinServerGame(args[0], port, args[2]);
            SettingsFile.saveSetting("LAST_USED_NAME", args[2]);
        }
        catch (NumberFormatException e) {
            return CommandStatus.INVALID_PORT;
        }
        catch (IOException e) {
            System.out.println("Error while joining a game: " + e.getLocalizedMessage());
            return CommandStatus.EXCEPTION;
        }

        return CommandStatus.NORMAL;
    }
}
