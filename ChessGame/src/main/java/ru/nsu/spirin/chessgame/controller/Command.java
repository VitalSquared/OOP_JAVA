package ru.nsu.spirin.chessgame.controller;

import ru.nsu.spirin.chessgame.scene.Scene;

public abstract class Command {
    private final Scene scene;

    protected Command(final Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    public abstract boolean execute(final String[] args, final boolean privileged);
}
