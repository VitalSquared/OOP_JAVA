package ru.nsu.spirin.battlecity.model.scene.battle.tank;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.scene.Entity;
import ru.nsu.spirin.battlecity.model.scene.battle.Bullet;

import java.util.Random;

public class EnemyTank extends Tank {
    private int cooldown = 0;
    private int health = 100;
    private boolean isDead = false;

    public EnemyTank(int posX, int posY, int sizeX, int sizeY) {
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
        if (isDead) {
            return false;
        }
        if (cooldown > 0) {
            cooldown--;
        }
        if (health < 0) {
            createNotification(new Notification(Context.DESTROY_SELF, getDirection()));
            isDead = true;
        }
        Random rnd = new Random();
        int rand = rnd.nextInt(50);
        switch(rand) {
            case 0 -> {
                move(Direction.UP);
            }
            case 10 -> {
                move(Direction.DOWN);
            }
            case 20 -> {
                move(Direction.LEFT);
            }
            case 30 -> {
                move(Direction.RIGHT);
            }
            case 40 -> {
                shoot();
            }
        }
        return false;
    }

    @Override
    public boolean detectCollision(Entity otherEntity) {
        if (otherEntity == null) {
            return false;
        }
        if (otherEntity instanceof Bullet) {
            if (((Bullet) otherEntity).getParentTank() != this) {
                health -= 10;
                return true;
            }
        }
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
