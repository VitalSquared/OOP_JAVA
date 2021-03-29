package ru.nsu.spirin.battlecity.model;

import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.model.map.BattleGrid;
import ru.nsu.spirin.battlecity.model.tank.Tank;

import java.io.IOException;

public class World {
    private Tank playerTank;
    private BattleGrid battleGrid;

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
}
