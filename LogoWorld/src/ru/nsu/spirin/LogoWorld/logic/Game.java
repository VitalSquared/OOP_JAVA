package ru.nsu.spirin.LogoWorld.logic;

import ru.nsu.spirin.LogoWorld.commands.Command;
import ru.nsu.spirin.LogoWorld.commands.CommandFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Game {
    String curCmd;
    String[] curArgs;
    Executor executor;
    Field field;
    CommandFactory commandFactory;

    public Game() throws IOException {
        field = new Field();
        executor = new Executor(field);
        commandFactory = new CommandFactory(executor);
    }

    public boolean parseCommand(String[] cmd) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        boolean validArgs;

        if (cmd.length == 0) {
            curArgs = null;
            curCmd = "";
            validArgs = false;
        }
        else {
            curCmd = cmd[0];
            curArgs = Arrays.copyOfRange(cmd, 1, cmd.length);
            Command instance = commandFactory.getCommand(curCmd);
            if (instance != null) validArgs = instance.validateArgs(curArgs);
            else validArgs = false;
        }
        return validArgs;
    }

    public boolean step() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Command instance = commandFactory.getCommand(curCmd);
        if (instance != null) return instance.execute(curArgs);
        else return false;
    }

    public Executor getExecutor() {
        return executor;
    }

    public Field getField() {
        return field;
    }
}
