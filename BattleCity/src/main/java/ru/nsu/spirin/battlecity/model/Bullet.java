package ru.nsu.spirin.battlecity.model;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;

public class Bullet extends Entity {
    public Bullet(int x, int y, Direction dir) {
        setPosition(new Point2D(x, y));
        setDirection(dir);
    }

    @Override
    public boolean move(Direction dir) {
        return false;
    }

    @Override
    public boolean update() {
        Point2D offset = Direction.convertDirectionToOffset(getDirection());
        Point2D curPos = getPosition();
        if (Math.sqrt((curPos.getX() * curPos.getX()) + (curPos.getY() * curPos.getY())) > 10) {
            setDestroyNotificationPending(true);
        }
        setPosition(new Point2D(curPos.getX() + offset.getX(), curPos.getY() + offset.getY()));
        return false;
    }
}
