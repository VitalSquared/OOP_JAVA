package ru.nsu.spirin.battlecity.model.tank;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;

public class PlayerTank extends Tank {

    public PlayerTank() {

    }

    @Override
    public boolean move(Direction dir) {
        setDirection(dir);
        Point2D offset = Direction.convertDirectionToOffset(dir);
        Point2D curPos = getPosition();
        setPosition(new Point2D(curPos.getX() + offset.getX(), curPos.getY() + offset.getY()));
        return true;
    }

    @Override
    public boolean update() {
        setShootNotificationPending(false);
        return false;
    }

    @Override
    public void shoot() {
        setShootNotificationPending(true);
    }
}
