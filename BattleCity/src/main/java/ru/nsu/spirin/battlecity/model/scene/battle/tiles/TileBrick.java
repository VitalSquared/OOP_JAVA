package ru.nsu.spirin.battlecity.model.scene.battle.tiles;

import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.scene.Entity;

public class TileBrick extends Entity {

    public TileBrick(int posX, int posY, int sizeX, int sizeY) {
        setPosition(new Point2D(posX, posY));
        setSize(new Point2D(sizeX, sizeY));
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean detectCollision(Entity otherEntity) {
        return false;
    }
}
