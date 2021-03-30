package ru.nsu.spirin.battlecity.model.scene.battle;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.model.scene.Entity;

public abstract class EntityMovable extends Entity {

    private Direction direction = Direction.UP;
    private int speed = 1;

    public Direction getDirection() {
        return direction;
    }

    protected void setDirection(Direction newDir) {
        this.direction = newDir;
    }

    protected void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public abstract boolean move(Direction dir);

    @Override
    public boolean update() {
        return false;
    }
}
