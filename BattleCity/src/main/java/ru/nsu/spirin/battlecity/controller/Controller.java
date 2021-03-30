package ru.nsu.spirin.battlecity.controller;

import ru.nsu.spirin.battlecity.model.scene.Scene;

public abstract class Controller {
    private Scene scene;

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    public abstract boolean action(Action action);
}
