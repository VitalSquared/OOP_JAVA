package ru.nsu.spirin.battlecity.model.scene.menu.selectable;

import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;

public enum SelectableType {
    SINGLEPLAYER(new Notification(Context.START_SINGLEPLAYER, null)),
    MULTIPLAYER(new Notification(Context.START_MULTIPLAYER, null)),
    HIGHSCORE(new Notification(Context.SHOW_HIGH_SCORES, null)),
    ABOUT(new Notification(Context.SHOW_ABOUT, null)),
    EXIT(new Notification(Context.EXIT, null)),

    REPLAY(new Notification(Context.START_SINGLEPLAYER, null)),
    MAPSELECTION(new Notification(Context.TO_MAP_SELECTION, null)),
    MAINMENU(new Notification(Context.TO_MAIN_MENU, null));

    private final Notification notification;

    SelectableType(Notification notification) {
        this.notification = notification;
    }

    public Notification getNotification() {
        return this.notification;
    }
}
