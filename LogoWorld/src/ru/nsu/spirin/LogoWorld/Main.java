package ru.nsu.spirin.LogoWorld;

import ru.nsu.spirin.LogoWorld.drawing.ConsoleView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        try {
            ConsoleView consoleView = new ConsoleView();
            consoleView.run();
        }
        catch (InterruptedException | IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
