package ru.nsu.spirin.logoworld.logic;

import input.ConsoleInput;
import input.Input;
import input.ProgramInput;
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
    private final Input input;
    private final CommandFactory commandFactory;

    /**
     * Creates Interpreter instance
     * @throws IOException if commands-properties file or program file are not found or invalid
     */
    public Interpreter(String programFileName, World world) throws IOException {
        logger.debug("Interpreter initialization.");
        if (programFileName == null) {
            this.input = new ConsoleInput();
        }
        else {
            this.input = new ProgramInput(programFileName);
        }
        this.commandFactory = new CommandFactory(input, world);
    }

    /**
     * Parses next command and its arguments.
     * @return true if command is parsed successfully and is valid.
     * @throws CommandsWorkflowException if command factory fails
     * @see Interpreter#step()
     */
    public boolean parseNextCommand() throws CommandsWorkflowException {
        String command = input.nextCommand();
        logger.debug("Interpreter parsing command: " + command);
        String[] cmd = command == null ? null : tokenizeCommand(command);
        boolean validArgs;

        if (cmd == null || cmd.length == 0 || command.equals("") || command.equals("EXIT")) {
            curArgs = null;
            curCmd = "";
            validArgs = true;
        }
        else {
            curCmd = cmd[0];
            curArgs = Arrays.copyOfRange(cmd, 1, cmd.length);
            Command instance = commandFactory.getCommand(curCmd);
            if (instance != null) {
                validArgs = instance.validateArgs(curArgs);
            }
            else {
                validArgs = false;
            }
        }
        logger.debug("Result of parsing: " + validArgs);
        return validArgs;
    }

    /**
     * Steps through the command execution.
     * Needs to parse command first
     * @return true if stepped successfully
     * @throws CommandsWorkflowException if commands factory fails
     * @see Interpreter#parseNextCommand()
     */
    public boolean step() throws CommandsWorkflowException {
        if (curCmd.equals("")) {
            logger.debug("Empty command. Skipping...");
            if (input.allowJump()) input.setNextCommand();
            return false;
        }

        logger.debug("Stepping through command...");
        Command instance = commandFactory.getCommand(curCmd);
        if (instance != null) {
            return instance.execute(curArgs);
        }
        else return false;
    }

    /**
     * Check if interpreter finished running program
     * @return true if finished
     */
    public boolean isFinished() {
        boolean finished = input.isFinished();
        if (finished) {
            logger.debug("Interpreter finished working. Program file will be closed.");
            input.close();
        }
        return finished;
    }

    /**
     * If you run program and encounter invalid command, you will be prompted to stop running.
     * @return true if you are running program
     */
    public boolean shouldAskForContinuation() {
        return input instanceof ProgramInput;
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
