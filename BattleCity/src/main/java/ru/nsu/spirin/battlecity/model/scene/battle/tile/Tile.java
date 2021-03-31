package ru.nsu.spirin.battlecity.model.scene.battle.tile;

import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.scene.Entity;
import ru.nsu.spirin.battlecity.model.scene.battle.Bullet;

public final class Tile extends Entity {
    private final boolean indestructible;
    private final boolean solid;
    private boolean destroyed;
    private int durability;
    private final TileType type;

    public Tile(TileType type, Point2D pos, Point2D size) {
        this.type = type;
        this.indestructible = type.isIndestructible();
        this.solid = type.isSolid();
        this.durability = type.getDurability();
        this.destroyed = false;
        setPosition(pos);
        setSize(size);
    }

    public TileType getTileType() {
        return this.type;
    }

    @Override
    public boolean update() {
        if (destroyed) {
            return false;
        }
        if (!indestructible && solid && durability <= 0) {
            destroyed = true;
            createNotification(new Notification(Context.DESTROY_SELF, null));
        }
        return true;
    }

    @Override
    public boolean onCollideWith(Entity otherEntity) {
        if (indestructible || !solid) return false;

        if (otherEntity instanceof Bullet) {
            durability--;
            return true;
        }
        return false;
    }
}
