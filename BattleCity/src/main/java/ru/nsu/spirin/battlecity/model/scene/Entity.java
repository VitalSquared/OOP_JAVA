package ru.nsu.spirin.battlecity.model.scene;

import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private int posX;
    private int posY;

    private int sizeX;
    private int sizeY;

    private final List<Notification> notificationList = new ArrayList<>();

    public Point2D getPosition() {
        return new Point2D(posX, posY);
    }

    public void setPosition(Point2D newPos) {
        this.posX = newPos.getX();
        this.posY = newPos.getY();
    }

    public Point2D getSize() {
        return new Point2D(sizeX, sizeY);
    }

    protected void setSize(Point2D newSize) {
        this.sizeX = newSize.getX();
        this.sizeY = newSize.getY();
    }

    public abstract boolean update();

    protected void createNotification(Notification notification) {
        notificationList.add(notification);
    }

    public void removeNotification(Notification notification) {
        notificationList.remove(notification);
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public abstract boolean onCollideWith(Entity otherEntity);
}
