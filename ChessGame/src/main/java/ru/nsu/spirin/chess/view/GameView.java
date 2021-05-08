package ru.nsu.spirin.chess.view;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;

public abstract class GameView {
    private final Scene scene;
    private final Controller controller;

    protected GameView(Scene scene, Controller controller) {
        this.scene = scene;
        this.controller = controller;
    }

    protected Scene getScene() {
        return this.scene;
    }

    protected Controller getController() {
        return this.controller;
    }

    public abstract void render();

    public abstract void close();
}
