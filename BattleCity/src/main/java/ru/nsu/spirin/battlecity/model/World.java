package ru.nsu.spirin.battlecity.model;

import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.map.BattleGrid;
import ru.nsu.spirin.battlecity.model.tank.Tank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final Tank playerTank;
    private final BattleGrid battleGrid;
    private final List<Entity> entityList = new ArrayList<>();

    public World(String mapFileName, Tank playerTank) throws IOException, InvalidBattleGridException {
        this.battleGrid = new BattleGrid(mapFileName);
        this.playerTank = playerTank;
    }

    public BattleGrid getBattleGrid() {
        return battleGrid;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void update() {
        List<Entity> toRemove = new ArrayList<>();
        for (var entity : entityList) {
            entity.update();
            if (entity.getDestroyNotificationPending()) {
                toRemove.add(entity);
            }
        }
        for (var entity : toRemove) {
            entityList.remove(entity);
        }
        if (playerTank.getShootNotificationPending()) {
            Point2D tankPos = playerTank.getPosition();
            entityList.add(new Bullet(tankPos.getX(), tankPos.getY(), playerTank.getDirection()));
            playerTank.update();
        }
    }
}
