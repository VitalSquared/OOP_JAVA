package ru.nsu.spirin.LogoWorld.logic;

import ru.nsu.spirin.LogoWorld.commands.Command;
import ru.nsu.spirin.LogoWorld.commands.CommandFactory;
import ru.nsu.spirin.LogoWorld.math.Pair;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Game {
    private String curCmd = "";
    private String[] curArgs = null;
    private Executor executor;
    private Field field;
    private CommandFactory commandFactory;

    /**
     * Creates new {@code Game} instance
     * @throws IOException if commands properties file is not found or invalid
     */
    public Game() throws IOException {
        field = new Field();
        executor = new Executor(field);
        commandFactory = new CommandFactory(executor);
    }

    /**
     * Parses given command and its arguments.
     * @param command - command and arguments
     * @return true if command is parsed successfully and is valid.
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @see ru.nsu.spirin.LogoWorld.logic.Game#step()
     */
    public boolean parseCommand(String command) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String[] cmd = command == null ? null : command.split(" +");
        boolean validArgs;

        if (cmd == null || cmd.length == 0 || command.equals("")) {
            curArgs = null;
            curCmd = "";
            validArgs = true;
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

    /**
     * Steps through the command execution.
     * Needs to parse command first
     * @return true if stepped successfully
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @see ru.nsu.spirin.LogoWorld.logic.Game#parseCommand(String)
     */
    public boolean step() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (curCmd.equals("")) return false;

        Command instance = commandFactory.getCommand(curCmd);
        if (instance != null) {
            boolean res = instance.execute(curArgs);
            if (res) updateExecutorAndField();
            return res;
        }
        else return false;
    }

    /**
     * Returns executor
     * @return executor
     */
    public Executor getExecutor() {
        return executor;
    }

    /**
     * Returns game field
     * @return game field
     */
    public Field getField() {
        return field;
    }

    private void updateExecutorAndField() {
        Pair pos = executor.getPosition();
        int r = pos.getFirst(), c = pos.getSecond();

        while (r < 0) r += field.getHeight();
        while (r >= field.getHeight()) r -= field.getHeight();

        while (c < 0) c += field.getWidth();
        while (c >= field.getWidth()) c -= field.getWidth();

        executor.setPosition(r, c);

        if (executor.getIsDrawing()) {
            field.setDrawn(r, c, true);
        }
    }
}
