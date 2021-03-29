package ru.nsu.spirin.battlecity.model.tank;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;

public abstract class Tank {
    private int posX;
    private int posY;
    private Direction direction = Direction.UP;

    public abstract boolean move(Direction dir);
    public abstract void shoot();

    public Direction getDirection() {
        return direction;
    }
    protected void setDirection(Direction newDir) {
        this.direction = newDir;
    }

    public Point2D getPosition() {
        return new Point2D(posX, posY);
    }
    protected void setPosition(Point2D newPos) {
        this.posX = newPos.getX();
        this.posY = newPos.getY();
    }
}
