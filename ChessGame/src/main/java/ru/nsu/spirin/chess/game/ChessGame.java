package ru.nsu.spirin.chess.game;

import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.model.scene.SceneState;
import ru.nsu.spirin.chess.view.ConsoleView;
import ru.nsu.spirin.chess.view.GameView;
import ru.nsu.spirin.chess.view.swing.SwingView;

import java.io.IOException;

public final class ChessGame implements GameEntity {
    private final GameView gameView;
    private final Scene    scene;

    public ChessGame(boolean useSwing) throws IOException {
        this.scene = new Scene();
        Controller controller = new Controller(this.scene);
        this.gameView = useSwing ?
                new SwingView(scene, controller) :
                new ConsoleView(scene, controller);
    }

    @Override
    public void run() {
        while (this.scene.getSceneState() != SceneState.NONE) {
            this.gameView.render();
        }
    }

    @Override
    public void close() {
        this.gameView.close();
    }
}
