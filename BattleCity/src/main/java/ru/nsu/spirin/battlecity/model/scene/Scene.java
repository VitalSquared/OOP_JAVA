package ru.nsu.spirin.battlecity.model.scene;

import ru.nsu.spirin.battlecity.controller.Action;
import ru.nsu.spirin.battlecity.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private final List<Entity> entityList = new ArrayList<>();
    private final List<Notification> notificationList = new ArrayList<>();

    public List<Entity> getEntityList() {
        return entityList;
    }

    public List<Notification> getNotificationList() {
        return this.notificationList;
    }

    public abstract void update();

    public abstract boolean action(Action action);
}
