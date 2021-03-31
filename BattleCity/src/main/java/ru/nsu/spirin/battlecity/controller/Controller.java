package ru.nsu.spirin.battlecity.controller;

import ru.nsu.spirin.battlecity.exceptions.InvalidControllerSceneException;
import ru.nsu.spirin.battlecity.model.scene.Scene;

public class Controller {
    private Scene scene;

    public Controller(Scene scene) throws InvalidControllerSceneException {
        if (scene == null) {
            throw new InvalidControllerSceneException("Scene was null!");
        }
        this.scene = scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public boolean action(Action action) {
        return scene.action(action);
    }
}
