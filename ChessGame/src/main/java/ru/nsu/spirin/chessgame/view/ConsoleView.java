package ru.nsu.spirin.chessgame.view;

import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.scene.Scene;

import java.util.Scanner;

public class ConsoleView implements GameView {
    private final Controller controller;
    private final Scanner scanner;

    public ConsoleView(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void render(Scene scene) {
        switch(scene.getSceneState()) {
            case MAIN_MENU -> {
                System.out.println("List of commands:\nnew_game\nhigh_scores\nabout\nexit");
                System.out.print("Enter command: ");
                boolean execResult = controller.execute(scanner.nextLine());
            }
            case GAME -> {
                System.out.println(scene.getBoard().toString());
                System.out.print("[" + scene.getBoard().getCurrentPlayer().getAlliance().toString() + "]" + " Enter command: ");
                boolean execResult = controller.execute(scanner.nextLine());
            }
        }
    }

    @Override
    public void close() {}
}
