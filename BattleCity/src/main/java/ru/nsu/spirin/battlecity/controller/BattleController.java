package ru.nsu.spirin.battlecity.controller;

import ru.nsu.spirin.battlecity.exceptions.InvalidControllerSceneException;
import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.model.scene.Scene;
import ru.nsu.spirin.battlecity.model.scene.battle.BattleScene;

public class BattleController extends Controller {

    public BattleController(Scene scene) throws InvalidControllerSceneException {
        if (!(scene instanceof BattleScene)) {
            throw new InvalidControllerSceneException("Battle Controller needs Battle Scene");
        }
        setScene(scene);
    }

    @Override
    public boolean action(Action action) {
        boolean result = false;
        switch (action) {
            case UP -> result = move(Direction.UP);
            case DOWN -> result = move(Direction.DOWN);
            case LEFT -> result = move(Direction.LEFT);
            case RIGHT -> result = move(Direction.RIGHT);
            case ACTION -> result = shoot();
        }
        return result;
    }

    private boolean move(Direction direction) {
        BattleScene battleScene = (BattleScene) getScene();
        return battleScene.getPlayerTank().move(direction);
    }

    private boolean shoot() {
        BattleScene battleScene = (BattleScene) getScene();
        battleScene.getPlayerTank().shoot();
        return true;
    }
}
