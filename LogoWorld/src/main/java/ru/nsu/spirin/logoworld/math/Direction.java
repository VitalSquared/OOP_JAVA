package ru.nsu.spirin.logoworld.math;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UNKNOWN;

    /**
     * Converts given direction to delta vector
     * @param dir direction to be converted
     * @return pair
     */
    public static Pair convertDirectionToDelta(Direction dir) {
        int dr = 0, dc = 0;
        if (dir != null) {
            switch (dir) {
                case UP -> dr = -1;
                case DOWN -> dr = 1;
                case LEFT -> dc = -1;
                case RIGHT -> dc = 1;
            }
        }
        return new Pair(dr, dc);
    }

    /**
     * Converts character to direction
     * @param ch character
     * @return direction
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
