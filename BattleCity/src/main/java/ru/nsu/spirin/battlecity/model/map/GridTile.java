package ru.nsu.spirin.battlecity.model.map;

public enum GridTile {
    BACKGROUND,
    BRICKS,
    LEAVES,
    WATER,
    UNKNOWN;

    public static GridTile charToTile(char ch) {
        switch(ch) {
            case '#' -> {
                return BRICKS;
            }
            case ' ' -> {
                return BACKGROUND;
            }
            case 'L' -> {
                return LEAVES;
            }
            case 'W' -> {
                return WATER;
            }
            default -> {
                return UNKNOWN;
            }
        }
    }
}
