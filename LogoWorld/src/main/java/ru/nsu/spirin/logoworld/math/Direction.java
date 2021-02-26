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
        int dx = 0, dy = 0;
        if (dir != null) {
            switch (dir) {
                case UP -> dy = -1;
                case DOWN -> dy = 1;
                case LEFT -> dx = -1;
                case RIGHT -> dx = 1;
            }
        }
        return new Pair(dx, dy);
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
