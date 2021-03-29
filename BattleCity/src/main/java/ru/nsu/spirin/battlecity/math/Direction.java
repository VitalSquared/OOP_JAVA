package ru.nsu.spirin.battlecity.math;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE;

    public static Point2D convertDirectionToOffset(Direction dir) {
        int dx = 0, dy = 0;
        if (dir != null) {
            switch (dir) {
                case UP -> dy = -1;
                case DOWN -> dy = 1;
                case LEFT -> dx = -1;
                case RIGHT -> dx = 1;
            }
        }
        return new Point2D(dx, dy);
    }

    public static double convertDirectionToAngleDegrees(Direction dir) {
        double angle = 0;
        if (dir != null) {
            switch (dir) {
                case UP -> angle = -90;
                case DOWN -> angle = 90;
                case LEFT -> angle = 180;
                case RIGHT -> angle = 0;
            }
        }
        return angle;
    }
}
