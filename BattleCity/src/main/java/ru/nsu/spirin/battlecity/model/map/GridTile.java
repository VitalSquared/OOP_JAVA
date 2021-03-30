package ru.nsu.spirin.battlecity.model.map;

public enum GridTile {
    NOTHING,
    BRICKS,
    LEAVES,
    WATER;

    public static GridTile charToTile(char ch) {
        switch(ch) {
            case '#' -> {
                return BRICKS;
            }
            case 'L' -> {
                return LEAVES;
            }
            case 'W' -> {
                return WATER;
            }
            default -> {
                return NOTHING;
            }
        }
    }
}
