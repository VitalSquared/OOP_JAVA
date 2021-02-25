package ru.nsu.spirin.logoworld.logic;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.commands.Command;
import ru.nsu.spirin.logoworld.commands.CommandFactory;
import ru.nsu.spirin.logoworld.exceptions.CommandsWorkflowException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interpreter {
    private static final Logger logger = Logger.getLogger(Interpreter.class);

    private String curCmd = "";
    private String[] curArgs = null;
    private final Program program;
    private final CommandFactory commandFactory;

    /**
     * Creates new {@code Interpreter} instance
     * @throws IOException if commands properties file is not found or invalid
     */
    public Interpreter(String programFileName, World world) throws IOException {
        logger.debug("Interpreter started initialization.");
        this.program = new Program(programFileName);
        this.commandFactory = new CommandFactory(program, world);
        logger.debug("Interpreter finished initialization.");
    }

    /**
     * Parses given command and its arguments.
     * @return true if command is parsed successfully and is valid.
     * @throws CommandsWorkflowException
     * @see Interpreter#step()
     */
    public boolean parseNextCommand() throws CommandsWorkflowException {
        String command = program.nextCommand();
        String[] cmd = command == null ? null : tokenizeCommand(command);
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
     * @see Interpreter#parseNextCommand()
     */
    public boolean step() throws CommandsWorkflowException {
        if (curCmd.equals("")) {
            program.setNextCommand();
            return false;
        }

        Command instance = commandFactory.getCommand(curCmd);
        if (instance != null) {
            return instance.execute(curArgs);
        }
        else return false;
    }

    public boolean isFinished() {
        boolean finished = program.isFinished();
        if (finished) program.close();
        return finished;
    }

    private String[] tokenizeCommand(String command) {
        List<String> list = new ArrayList<>();

        char[] characters = command.toCharArray();
        int bracketBalance = 0;
        StringBuilder curToken = new StringBuilder();

        for (char ch : characters) {
            if (ch == ' ') {
                if (bracketBalance == 0) {
                    if (curToken.length() != 0) {
                        list.add(curToken.toString());
                    }
                    curToken.setLength(0);
                    continue;
                }
            }
            if (ch == '(') bracketBalance++;
            else if (ch == ')') bracketBalance--;
            curToken.append(ch);
        }

        if (curToken.length() != 0) {
            list.add(curToken.toString());
        }
        curToken.setLength(0);

        return list.toArray(new String[0]);
    }
}
