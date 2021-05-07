package ru.nsu.spirin.chess.view;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;

public abstract class GameView {
    private final Controller controller;

    protected GameView(Controller controller) {
        this.controller = controller;
    }

    protected Controller getController() {
        return this.controller;
    }

    public abstract void render(Scene scene);

    public abstract void close();
}
