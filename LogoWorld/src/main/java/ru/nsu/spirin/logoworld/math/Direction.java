package ru.nsu.spirin.logoworld.math;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UNKNOWN;

    /**
     * Converts given {@code Direction} to delta vector
     * @param direction {@code Direction} to be converted
     * @return delta vector which represents direction
     */
    public static Pair convertDirectionToDelta(Direction direction) {
        int dr = 0, dc = 0;
        if (direction != null) {
            switch (direction) {
                case UP -> dr = -1;
                case DOWN -> dr = 1;
                case LEFT -> dc = -1;
                case RIGHT -> dc = 1;
            }
        }
        return new Pair(dr, dc);
    }

    /**
     * Converts given character (U, D, L, R) to {@code Direction}
     * @param ch character to be converted
     * @return direction which represents character
     */
    public static Direction convertCharacterToDirection(char ch) {
        return switch(ch) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> UNKNOWN;
        };
    }
}
