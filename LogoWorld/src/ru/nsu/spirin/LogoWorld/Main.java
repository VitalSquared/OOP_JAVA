package ru.nsu.spirin.LogoWorld;

import ru.nsu.spirin.LogoWorld.drawing.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        try {
            consoleView.run();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
