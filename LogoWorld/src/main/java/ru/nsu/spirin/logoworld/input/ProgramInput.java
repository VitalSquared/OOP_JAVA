package ru.nsu.spirin.logoworld.input;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramInput implements Input {
    private Scanner scanner = null;
    private static final Logger logger = Logger.getLogger(ProgramInput.class);
    private final List<String> commandsOrder = new ArrayList<>();
    private int curCommand = 0;

    public ProgramInput(String fileName) throws FileNotFoundException {
        logger.debug("Loading program: " + fileName + " ...");
        scanner = new Scanner(new FileInputStream(fileName));
        while (scanner.hasNext()) {
            commandsOrder.add(scanner.nextLine());
        }
    }

    public String nextCommand() {
       return commandsOrder.get(curCommand);
    }

    public void setNextCommand() {
        curCommand++;
    }

    public void setNextCommand(int nextCommand) {
        curCommand = nextCommand - 1;
    }

    public boolean isFinished() {
        return curCommand >= commandsOrder.size();
    }

    @Override
    public boolean allowJump() {
        return true;
    }

    public void close() {
        logger.debug("ProgramInput closing.");
        if (scanner != null) scanner.close();
    }
}
