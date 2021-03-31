package ru.nsu.spirin.battlecity.model.scene.mainmenu.selectable;

import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;

public enum SelectableType {
    SINGLEPLAYER(new Notification(Context.START_SINGLEPLAYER, null)),
    MULTIPLAYER(new Notification(Context.START_MULTIPLAYER, null)),
    HIGHSCORE(new Notification(Context.SHOW_HIGH_SCORES, null)),
    ABOUT(new Notification(Context.SHOW_ABOUT, null)),
    EXIT(new Notification(Context.EXIT, null));

    private final Notification notification;

    SelectableType(Notification notification) {
        this.notification = notification;
    }

    public Notification getNotification() {
        return this.notification;
    }
}
