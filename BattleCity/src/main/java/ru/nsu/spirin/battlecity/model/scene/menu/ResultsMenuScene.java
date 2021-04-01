package ru.nsu.spirin.battlecity.model.scene.menu;

import ru.nsu.spirin.battlecity.controller.Action;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.scene.Scene;
import ru.nsu.spirin.battlecity.model.scene.menu.infocard.InfoCard;
import ru.nsu.spirin.battlecity.model.scene.menu.infocard.InfoCardType;
import ru.nsu.spirin.battlecity.model.scene.menu.selectable.MenuSelectable;
import ru.nsu.spirin.battlecity.model.scene.menu.selectable.SelectableType;

import java.util.List;

public class ResultsMenuScene extends Scene {

    private boolean gameLost = false;
    private int score;
    private MenuSelectable activeButton;

    public ResultsMenuScene(boolean gameLost, int score) {
        this.gameLost = gameLost;
        this.score = score;

        int PADDING_LEFT = 200;
        getEntityList().add(new InfoCard(gameLost ? InfoCardType.GAMELOST : InfoCardType.GAMEWON, new Point2D(PADDING_LEFT, 50), new Point2D(124, 60)));

        MenuSelectable replayButton = new MenuSelectable(SelectableType.REPLAY, new Point2D(PADDING_LEFT, 135), new Point2D(200, 50));
        MenuSelectable mapSelectionButton = new MenuSelectable(SelectableType.MAPSELECTION, new Point2D(PADDING_LEFT, 210), new Point2D(200, 50));
        MenuSelectable mainmenuButton = new MenuSelectable(SelectableType.MAINMENU, new Point2D(PADDING_LEFT, 285), new Point2D(200, 50));

        replayButton.setPrev(null);
        replayButton.setNext(mapSelectionButton);

        mapSelectionButton.setPrev(replayButton);
        mapSelectionButton.setNext(mainmenuButton);

        mainmenuButton.setPrev(mapSelectionButton);
        mainmenuButton.setNext(null);

        replayButton.setSelected(true);
        activeButton = replayButton;

        getEntityList().add(replayButton);
        getEntityList().add(mapSelectionButton);
        getEntityList().add(mainmenuButton);
    }

    @Override
    public void update() {
        for (var entity : getEntityList()) {
            entity.update();
            List<Notification> notificationList = entity.getNotificationList();
            for (int i = 0; i < notificationList.size(); i++) {
                var notification = notificationList.get(i);
                switch (notification.getContext()) {
                    case START_SINGLEPLAYER -> {
                        getNotificationList().add(new Notification(Context.START_SINGLEPLAYER, null));
                    }
                    /*case START_MULTIPLAYER -> {

                    }
                    case SHOW_HIGH_SCORES -> {

                    }
                    case SHOW_ABOUT -> {

                    }*/
                    case EXIT -> {
                        getNotificationList().add(new Notification(Context.EXIT, null));
                    }
                    case TO_MAIN_MENU -> {
                        getNotificationList().add(new Notification(Context.TO_MAIN_MENU, null));
                    }
                }
            }
            notificationList.clear();
        }
    }

    @Override
    public boolean action(Action action) {
        switch(action) {
            case UP -> {
                if (activeButton.getPrev() != null) {
                    activeButton.setSelected(false);
                    activeButton = activeButton.getPrev();
                    activeButton.setSelected(true);
                }
            }
            case DOWN -> {
                if (activeButton.getNext() != null) {
                    activeButton.setSelected(false);
                    activeButton = activeButton.getNext();
                    activeButton.setSelected(true);
                }
            }
            case ACTION -> activeButton.onPressed();
        }
        return true;
    }
}
