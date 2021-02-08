package ru.nsu.spirin.LogoWorld.logic;

import ru.nsu.spirin.LogoWorld.commands.Command;
import ru.nsu.spirin.LogoWorld.commands.DrawCommand;
import ru.nsu.spirin.LogoWorld.commands.InitCommand;
import ru.nsu.spirin.LogoWorld.commands.MoveCommand;
import ru.nsu.spirin.LogoWorld.commands.TeleportCommand;
import ru.nsu.spirin.LogoWorld.commands.WardCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Game {
    String curCmd;
    String[] curArgs;
    Map<String, Command> commandsMap;
    Executor executor;
    Field field;

    public Game() {
        field = new Field();
        executor = new Executor();
        commandsMap = new HashMap<>();
        commandsMap.put("DRAW", new DrawCommand(executor));
        commandsMap.put("INIT", new InitCommand(executor, field));
        commandsMap.put("MOVE", new MoveCommand(executor, field));
        commandsMap.put("TELEPORT", new TeleportCommand(executor));
        commandsMap.put("WARD", new WardCommand(executor));
    }

    public boolean parseCommand(String[] cmd) {
        boolean validArgs;

        if (cmd.length == 0) {
            curArgs = null;
            curCmd = "";
            validArgs = false;
        }
        else {
            curCmd = cmd[0];
            curArgs = Arrays.copyOfRange(cmd, 1, cmd.length);

            if (commandsMap.containsKey(curCmd)) validArgs = commandsMap.get(curCmd).validateArgs(curArgs);
            else validArgs = false;
        }
        return validArgs;
    }

    public boolean step() {
        return commandsMap.get(curCmd).execute(curArgs);
    }

    public Executor getExecutor() {
        return executor;
    }

    public Field getField() {
        return field;
    }
}
