package ru.nsu.spirin.logoworld.logic;

import ru.nsu.spirin.logoworld.commands.Command;
import ru.nsu.spirin.logoworld.commands.CommandFactory;
import ru.nsu.spirin.logoworld.exceptions.CommandsWorkflowException;

import java.io.IOException;
import java.util.Arrays;

public class Interpreter {
    private String curCmd = "";
    private String[] curArgs = null;
    private final CommandFactory commandFactory;

    /**
     * Creates new {@code Interpreter} instance
     * @throws IOException if commands properties file is not found or invalid
     */
    public Interpreter(World world) throws IOException {
        this.commandFactory = new CommandFactory(world);
    }

    /**
     * Parses given command and its arguments.
     * @param command - command and arguments
     * @return true if command is parsed successfully and is valid.
     * @throws CommandsWorkflowException
     * @see Interpreter#step()
     */
    public boolean parseCommand(String command) throws CommandsWorkflowException {
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
     * @throws CommandsWorkflowException
     * @see Interpreter#parseCommand(String)
     */
    public boolean step() throws CommandsWorkflowException {
        if (curCmd.equals("")) return false;

        Command instance = commandFactory.getCommand(curCmd);
        if (instance != null) {
            return instance.execute(curArgs);
        }
        else return false;
    }
}
