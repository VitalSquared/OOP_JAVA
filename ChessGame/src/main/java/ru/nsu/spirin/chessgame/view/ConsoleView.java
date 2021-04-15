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
                printCommands();
                System.out.print("Enter command: ");
                boolean execResult = controller.execute(scanner.nextLine(), false);
            }
            case GAME -> {
                System.out.println(scene.getBoard().toString());
                System.out.print("[" + scene.getBoard().getCurrentPlayer().getAlliance().toString() + "]" + " Enter command: ");
                if (!scene.getBoard().getCurrentPlayer().isAI()) {
                    boolean execResult = controller.execute(scanner.nextLine(), false);
                }
                else {
                    boolean execResult = controller.execute("ai_move", true);
                }
            }
        }
    }

    @Override
    public void close() {}

    private void printCommands() {
        System.out.println("List of commands:");
        System.out.println("new_game -singleplayer <white_player_name> <black_player_name> <white_ai [true|false]> <black_ai [true|false]>");
        System.out.println("high_scores");
        System.out.println("about");
        System.out.println("exit");
    }
}
