package ru.nsu.spirin.battlecity.model.scene.battle;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;

public class Bullet extends EntityMovable {
    public Bullet(int x, int y, int sizeX, int sizeY, Direction dir) {
        setPosition(new Point2D(x, y));
        setSize(new Point2D(sizeX, sizeY));
        setDirection(dir);
        setSpeed(7);
    }

    @Override
    public boolean move(Direction dir) {
        createNotification(new Notification(Context.MOVE, dir));
        return false;
    }

    @Override
    public boolean update() {
        Point2D curPos = getPosition();
        if (curPos.getX() < 0 || curPos.getX() > 1280 || curPos.getY() < 0 || curPos.getY() > 720) {
            createNotification(new Notification(Context.DESTROY_SELF, getDirection()));
            return false;
        }
        move(getDirection());
        return false;
    }
}
