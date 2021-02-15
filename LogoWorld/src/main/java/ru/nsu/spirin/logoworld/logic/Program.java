package ru.nsu.spirin.logoworld.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program {
    private Scanner scanner = null;

    public Program(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new FileInputStream(fileName));
    }

    public String nextCommand() {
        return scanner.nextLine().trim();
    }

    public boolean isFinished() {
        return !scanner.hasNext();
    }

    public void close() {
        if (scanner != null) scanner.close();
    }
}
