package ru.nsu.spirin.battlecity.model.scene.mainmenu.infocard;

import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.scene.Entity;

public class InfoCard extends Entity {
    private final InfoCardType type;

    public InfoCard(InfoCardType type, Point2D pos, Point2D size) {
        this.type = type;
        setPosition(pos);
        setSize(size);
    }

    public InfoCardType getInfoCardType() {
        return this.type;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean onCollideWith(Entity otherEntity) {
        return false;
    }
}
