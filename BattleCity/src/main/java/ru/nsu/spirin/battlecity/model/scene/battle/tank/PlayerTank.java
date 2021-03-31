package ru.nsu.spirin.battlecity.model.scene.battle.tank;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.scene.Entity;
import ru.nsu.spirin.battlecity.model.scene.battle.Bullet;

public final class PlayerTank extends Tank {

    private int cooldown = 0;

    public PlayerTank(int posX, int posY, int sizeX, int sizeY) {
        setPosition(new Point2D(posX, posY));
        setSize(new Point2D(sizeX, sizeY));
        setSpeed(2);
    }

    @Override
    public boolean canGoThrough(Entity entity) {
        if (entity instanceof Bullet) {
            return ((Bullet)entity).getParentTank() == this;
        }
        return false;
    }

    @Override
    public boolean move(Direction dir) {
        setDirection(dir);
        createNotification(new Notification(Context.MOVE, getDirection()));
        return true;
    }

    @Override
    public boolean update() {
        if (cooldown > 0) {
            cooldown--;
        }
        return false;
    }

    @Override
    public boolean onCollideWith(Entity otherEntity) {
        return false;
    }

    @Override
    public void shoot() {
        if (cooldown <= 0) {
            createNotification(new Notification(Context.SHOOT, getDirection()));
            cooldown = 10;
        }
    }
}
