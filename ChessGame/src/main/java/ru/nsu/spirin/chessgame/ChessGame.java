package ru.nsu.spirin.chessgame;

import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.scene.SceneState;
import ru.nsu.spirin.chessgame.view.ConsoleView;
import ru.nsu.spirin.chessgame.view.GameView;
import ru.nsu.spirin.chessgame.view.swing.SwingView;

public final class ChessGame {
    private final GameView gameView;
    private final Scene scene;

    public ChessGame(final boolean useSwing) {
        scene = new Scene();
        Controller controller = new Controller(scene);
        if (useSwing) {
            gameView = new SwingView(controller);
        }
        else {
            gameView = new ConsoleView(controller);
        }
    }

    public void run() {
        boolean shouldExit = false;
        while (!shouldExit) {
            gameView.render(scene);
            if (scene.getSceneState() == SceneState.DESTROY_SELF) {
                shouldExit = true;
            }
            try {
                Thread.sleep(100);
            }
            catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
        }
    }

    public void close() {
        gameView.close();
    }
}
