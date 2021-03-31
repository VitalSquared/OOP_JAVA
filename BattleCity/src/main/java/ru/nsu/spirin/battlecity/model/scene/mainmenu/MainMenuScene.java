package ru.nsu.spirin.battlecity.model.scene.mainmenu;

import ru.nsu.spirin.battlecity.controller.Action;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.scene.Scene;
import ru.nsu.spirin.battlecity.model.scene.mainmenu.infocard.InfoCard;
import ru.nsu.spirin.battlecity.model.scene.mainmenu.infocard.InfoCardType;
import ru.nsu.spirin.battlecity.model.scene.mainmenu.selectable.MenuSelectable;
import ru.nsu.spirin.battlecity.model.scene.mainmenu.selectable.SelectableType;

import java.util.List;

public class MainMenuScene extends Scene {

    private MenuSelectable activeButton = null;
    private final int PADDING_LEFT = 200;

    public MainMenuScene() {
        getEntityList().add(new InfoCard(InfoCardType.LOGO, new Point2D(PADDING_LEFT, 50), new Point2D(188, 68)));
        getEntityList().add(new InfoCard(InfoCardType.COPYRIGHT, new Point2D(PADDING_LEFT, 500), new Point2D(256, 54)));

        MenuSelectable singleplayerButton = new MenuSelectable(SelectableType.SINGLEPLAYER, new Point2D(PADDING_LEFT, 125), new Point2D(200, 50));
        MenuSelectable multiplayerButton = new MenuSelectable(SelectableType.MULTIPLAYER, new Point2D(PADDING_LEFT, 200), new Point2D(200, 50));
        MenuSelectable highscoreButton = new MenuSelectable(SelectableType.HIGHSCORE, new Point2D(PADDING_LEFT, 275), new Point2D(200, 50));
        MenuSelectable aboutButton = new MenuSelectable(SelectableType.ABOUT, new Point2D(PADDING_LEFT, 350), new Point2D(200, 50));
        MenuSelectable exitButton = new MenuSelectable(SelectableType.EXIT, new Point2D(PADDING_LEFT, 425), new Point2D(200, 50));

        singleplayerButton.setPrev(null);
        singleplayerButton.setNext(multiplayerButton);

        multiplayerButton.setPrev(singleplayerButton);
        multiplayerButton.setNext(highscoreButton);

        highscoreButton.setPrev(multiplayerButton);
        highscoreButton.setNext(aboutButton);

        aboutButton.setPrev(highscoreButton);
        aboutButton.setNext(exitButton);

        exitButton.setPrev(aboutButton);
        exitButton.setNext(null);

        singleplayerButton.setSelected(true);
        activeButton = singleplayerButton;

        getEntityList().add(singleplayerButton);
        getEntityList().add(multiplayerButton);
        getEntityList().add(highscoreButton);
        getEntityList().add(aboutButton);
        getEntityList().add(exitButton);
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
                    case START_MULTIPLAYER -> {

                    }
                    case SHOW_HIGH_SCORES -> {

                    }
                    case SHOW_ABOUT -> {

                    }
                    case EXIT -> {
                        getNotificationList().add(new Notification(Context.EXIT, null));
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
            case ACTION -> {
                activeButton.onPressed();
            }
        }
        return true;
    }


}
