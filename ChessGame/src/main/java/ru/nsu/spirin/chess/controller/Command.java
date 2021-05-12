package ru.nsu.spirin.chess.controller;

import ru.nsu.spirin.chess.scene.Scene;

public abstract class Command {
    private final Scene scene;

    protected Command(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    public abstract CommandStatus execute(String[] args);
}
