package ru.nsu.spirin.logoworld.logic;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    private Scanner scanner = null;
    private static final Logger logger = Logger.getLogger(Program.class);
    private final List<String> commandsOrder = new ArrayList<>();
    private int curCommand = 0;

    public Program(String fileName) throws FileNotFoundException {
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

    public void close() {
        logger.debug("Program closing.");
        if (scanner != null) scanner.close();
    }
}
