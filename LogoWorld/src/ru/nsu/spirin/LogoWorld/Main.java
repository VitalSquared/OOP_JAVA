package ru.nsu.spirin.LogoWorld;

import ru.nsu.spirin.LogoWorld.drawing.ConsoleView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Too many arguments. Specify FILE to read program from or NOTHING to read from console\n");
            return;
        }
        try {
            ConsoleView consoleView = new ConsoleView(args.length == 0 ? "" : args[0]);
            consoleView.run();
        }
        catch (InterruptedException | IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
