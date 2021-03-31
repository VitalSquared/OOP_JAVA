package ru.nsu.spirin.battlecity;

import ru.nsu.spirin.battlecity.controller.BattleController;
import ru.nsu.spirin.battlecity.controller.Controller;
import ru.nsu.spirin.battlecity.exceptions.FactoryException;
import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.exceptions.InvalidControllerSceneException;
import ru.nsu.spirin.battlecity.model.scene.Scene;
import ru.nsu.spirin.battlecity.model.scene.battle.BattleScene;
import ru.nsu.spirin.battlecity.view.GameView;
import ru.nsu.spirin.battlecity.view.swing.SwingView;

import java.io.IOException;

public class BattleCityGame {

    private static final int MAX_FPS = 30;

    GameView gameView;
    Scene curScene;
    Controller controller;

    public BattleCityGame() throws IOException, InvalidBattleGridException, InvalidControllerSceneException {
        curScene = new BattleScene("maps/map1.txt");
        controller = new BattleController(curScene);
        gameView = new SwingView(controller);
    }

    public void run() throws FactoryException {
        while(true) {
            gameView.render(curScene);
            try {
                Thread.sleep(1000 / MAX_FPS);
            }
            catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            curScene.update();
        }
    }
}
