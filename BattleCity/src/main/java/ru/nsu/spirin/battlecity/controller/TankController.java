package ru.nsu.spirin.battlecity.controller;

import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.model.tank.Tank;

public class TankController {
    private final Tank playerTank;

    public TankController(Tank playerTank) {
        this.playerTank = playerTank;
    }

    public boolean moveUp() {
        return playerTank.move(Direction.UP);
    }

    public boolean moveDown() {
        return playerTank.move(Direction.DOWN);
    }

    public boolean moveLeft() {
        return playerTank.move(Direction.LEFT);
    }

    public boolean moveRight() {
        return playerTank.move(Direction.RIGHT);
    }

    public void shoot() {
        playerTank.shoot();
    }
}
