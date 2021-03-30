package ru.nsu.spirin.battlecity.model.tank;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;

public class PlayerTank extends Tank {

    public PlayerTank(int posX, int posY, int sizeX, int sizeY) {
        setPosition(new Point2D(posX, posY));
        setSize(new Point2D(sizeX, sizeY));
        setSpeed(2);
    }

    @Override
    public boolean move(Direction dir) {
        setDirection(dir);
        createNotification(new Notification(Context.MOVE, getDirection()));
        return true;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public void shoot() {
        createNotification(new Notification(Context.SHOOT, getDirection()));
    }
}
