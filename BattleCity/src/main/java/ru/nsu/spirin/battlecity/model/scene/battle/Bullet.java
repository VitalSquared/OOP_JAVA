package ru.nsu.spirin.battlecity.model.scene.battle;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.scene.Entity;
import ru.nsu.spirin.battlecity.model.scene.battle.tank.Tank;

public class Bullet extends EntityMovable {
    private final Tank parentTank;

    public Bullet(Tank parentTank) {
        this.parentTank = parentTank;
        Point2D tankPos = parentTank.getPosition();
        Point2D tankSize = parentTank.getSize();
        setSize(new Point2D(12, 12));
        int x = tankPos.getX() + (tankSize.getX() / 2) - (getSize().getX() / 2);
        int y = tankPos.getY() + (tankSize.getY() / 2) - (getSize().getY() / 2);
        setPosition(new Point2D(x, y));
        setDirection(parentTank.getDirection());
        setSpeed(10);
    }

    public Tank getParentTank() {
        return this.parentTank;
    }

    @Override
    public boolean canGoThrough(Entity entity) {
        return entity == parentTank;
    }

    @Override
    public boolean move(Direction dir) {
        createNotification(new Notification(Context.MOVE, dir));
        return false;
    }

    @Override
    public boolean update() {
        Point2D curPos = getPosition();
        /*if (curPos.getX() < 0 || curPos.getX() > 1280 || curPos.getY() < 0 || curPos.getY() > 720) {
            createNotification(new Notification(Context.DESTROY_SELF, getDirection()));
            return false;
        }*/
        move(getDirection());
        return false;
    }

    @Override
    public boolean detectCollision(Entity otherEntity) {
        if (otherEntity == null) {
            return false;
        }
        createNotification(new Notification(Context.DESTROY_SELF, getDirection()));
        return true;
    }
}
