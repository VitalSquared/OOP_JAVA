package ru.nsu.spirin.logoworld.input;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private static final Logger logger = Logger.getLogger(ProgramInput.class);

    private Scanner scanner = null;
    private String prevCommand = null;

    public ConsoleInput() {
        logger.debug("Console Input initialized.");
        scanner = new Scanner(System.in);
    }

    @Override
    public String nextCommand() {
        prevCommand = scanner.nextLine().trim();
        return prevCommand;
    }

    @Override
    public void setNextCommand() {}

    @Override
    public void setNextCommand(int nextCommand) {}

    @Override
    public boolean isFinished() {
        return prevCommand != null && prevCommand.equals("EXIT");
    }

    @Override
    public boolean allowJump() {
        return false;
    }

    @Override
    public void close() {}
}
