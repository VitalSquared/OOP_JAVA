package ru.nsu.spirin.logoworld.input;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.exceptions.InvalidInputException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramInput implements Input {
    private static final Logger logger = Logger.getLogger(ProgramInput.class);

    private int curCommand = 0;
    private final List<String> commandsOrder = new ArrayList<>();

    /**
     * Initialize input from file with program
     *
     * @param fileName file name which contains program
     * @throws FileNotFoundException file not found
     */
    public ProgramInput(String fileName) throws FileNotFoundException {
        logger.debug("Loading program: " + fileName + " ...");
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        while (scanner.hasNext()) {
            commandsOrder.add(scanner.nextLine());
        }
        scanner.close();
    }

    @Override
    public boolean isFinished() {
        return curCommand >= commandsOrder.size();
    }

    @Override
    public boolean allowJump() {
        return true;
    }

    @Override
    public String nextCommand() throws InvalidInputException {
        if (curCommand < 0 || curCommand >= commandsOrder.size()) {
            throw new InvalidInputException("");
        }
        return commandsOrder.get(curCommand);
    }

    @Override
    public void setNextCommand(Integer nextCommand) {
        if (nextCommand == null) {
            curCommand++;
        }
        else {
            curCommand = nextCommand - 1;
        }
    }
}
