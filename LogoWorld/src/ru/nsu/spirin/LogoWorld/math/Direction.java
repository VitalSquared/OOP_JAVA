package ru.nsu.spirin.LogoWorld.math;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UNKNOWN;

    public static Pair dirToDelta(Direction dir) {
        int dr = 0, dc = 0;
        switch (dir) {
            case UP: dr = -1; dc = 0; break;
            case DOWN: dr = 1; dc = 0; break;
            case LEFT: dr = 0; dc = -1; break;
            case RIGHT: dr = 0; dc = 1; break;
        }
        return new Pair(dr, dc);
    }

    public static Direction stringToDir(String str) {
        return switch(str) {
            case "U" -> UP;
            case "D" -> DOWN;
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> UNKNOWN;
        };
    }
}
