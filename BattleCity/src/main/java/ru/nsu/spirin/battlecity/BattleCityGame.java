package ru.nsu.spirin.battlecity;

import ru.nsu.spirin.battlecity.controller.Controller;
import ru.nsu.spirin.battlecity.exceptions.FactoryException;
import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.exceptions.InvalidControllerSceneException;
import ru.nsu.spirin.battlecity.model.scene.Scene;
import ru.nsu.spirin.battlecity.model.scene.battle.BattleScene;
import ru.nsu.spirin.battlecity.model.scene.menu.MainMenuScene;
import ru.nsu.spirin.battlecity.model.scene.menu.ResultsMenuScene;
import ru.nsu.spirin.battlecity.view.GameView;
import ru.nsu.spirin.battlecity.view.swing.SwingView;

import java.io.IOException;

public final class BattleCityGame {

    private static final int MAX_FPS = 30;

    GameView gameView;
    Scene curScene;
    Controller controller;

    public BattleCityGame() throws IOException, InvalidControllerSceneException {
        curScene = new MainMenuScene();
        controller = new Controller(curScene);
        gameView = new SwingView(controller);
    }

    public void run() throws FactoryException, IOException, InvalidBattleGridException {
        boolean shouldExit = false;
        while(!shouldExit) {
            gameView.render(curScene);
            try {
                Thread.sleep(1000 / MAX_FPS);
            }
            catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            curScene.update();
            for (var notification : curScene.getNotificationList()) {
                switch (notification.getContext()) {
                    case START_SINGLEPLAYER -> {
                        curScene = new BattleScene("maps/map1.txt");
                        controller.setScene(curScene);
                    }
                    case EXIT -> shouldExit = true;
                    case TO_MAIN_MENU -> {
                        curScene = new MainMenuScene();
                        controller.setScene(curScene);
                    }
                    case GAME_LOST -> {
                        curScene = new ResultsMenuScene(true, 0);
                        controller.setScene(curScene);
                    }
                    case GAME_WON -> {
                        curScene = new ResultsMenuScene(false, 10);
                        controller.setScene(curScene);
                    }
                }
            }
            curScene.getNotificationList().clear();
        }
    }

    public void close () {
        gameView.close();
    }
}
