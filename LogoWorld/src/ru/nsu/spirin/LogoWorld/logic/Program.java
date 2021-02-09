package ru.nsu.spirin.LogoWorld.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program {
    private String prevCommand = "";
    private Scanner scanner = null;
    private boolean isFile = false;

    public Program(String fileName) {
        try {
            scanner = new Scanner(new FileInputStream(fileName));
            isFile = true;
        }
        catch (FileNotFoundException e) {
            scanner = new Scanner(System.in);
            isFile = false;
        }
    }

    public String nextCommand() {
        prevCommand = scanner.nextLine();
        return prevCommand;
    }

    public boolean isFinished() {
        return isFile ? !scanner.hasNext() : prevCommand.equals("EXIT");
    }

    public boolean requestContinuation() {
        return isFile;
    }

    public void close() {
        if (isFile) {
            scanner.close();
        }
    }
}
