package ru.nsu.spirin.chess;

import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.SceneState;
import ru.nsu.spirin.chess.view.ConsoleView;
import ru.nsu.spirin.chess.view.GameView;
import ru.nsu.spirin.chess.view.swing.SwingView;

import java.io.IOException;

public final class ChessGame {
    private final GameView gameView;
    private final Scene    scene;

    public ChessGame(boolean useSwing) throws IOException {
        this.scene = new Scene();
        Controller controller = new Controller(this.scene);
        this.gameView = useSwing ?
                new SwingView(scene, controller) :
                new ConsoleView(scene, controller);
    }

    public void run() {
        while (this.scene.getSceneState() != SceneState.NONE) {
            this.gameView.render();
        }
    }

    public void close() {
        this.gameView.close();
    }
}
