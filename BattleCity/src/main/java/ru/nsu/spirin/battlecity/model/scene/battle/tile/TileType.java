package ru.nsu.spirin.battlecity.model.scene.battle.tile;

public enum TileType {
    BRICK (2, false, true),
    BORDER (0, true, true),
    LEAVES(0, true, false),
    EAGLE (10, false, true);

    private final boolean indestructible;
    private final boolean solid;
    private final int durability;

    TileType(int durability, boolean indestructible, boolean solid) {
        this.durability = durability;
        this.indestructible = indestructible;
        this.solid = solid;
    }

    public boolean isIndestructible() {
        return this.indestructible;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public int getDurability() {
        return this.durability;
    }

}
