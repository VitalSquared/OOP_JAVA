package ru.nsu.spirin.logoworld.logic;

import ru.nsu.spirin.logoworld.math.Direction;
import ru.nsu.spirin.logoworld.math.Pair;

public class Turtle {
    private int x;
    private int y;
    private boolean isDrawing;

    /**
     * Create turtle
     */
    public Turtle() {
        this.isDrawing = false;
    }

    /**
     * Check if turtle is currently drawing
     *
     * @return true if turtle is drawing
     */
    public boolean getIsDrawing() {
        return isDrawing;
    }

    /**
     * Set drawing staet of turtle
     *
     * @param isDrawing new drawing state
     */
    public void setIsDrawing(boolean isDrawing) {
        this.isDrawing = isDrawing;
    }

    /**
     * Set turtle position
     *
     * @param x x-position
     * @param y y-position
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Move turtle in direction
     *
     * @param dir direction
     */
    public void move(Direction dir) {
        Pair delta = Direction.convertDirectionToDelta(dir);
        this.x += delta.getFirst();
        this.y += delta.getSecond();
    }

    /**
     * Get turtle position
     *
     * @return (x, y) - turtle position
     */
    public Pair getPosition() {
        return new Pair(x, y);
    }
}
