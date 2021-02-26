package ru.nsu.spirin.logoworld.logic;

import ru.nsu.spirin.logoworld.math.Pair;

public class Field {
    private int width;
    private int height;
    private boolean[] map;

    /**
     * Create empty field
     */
    public Field() {
        this.width = 0;
        this.height = 0;
        map = null;
    }

    /**
     * Set field size
     * @param width width of field
     * @param height height of field
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        map = new boolean[width * height];
    }

    /**
     * Get field size
     * @return (width, height) - field size
     */
    public Pair getSize() {
        return new Pair(this.width, this.height);
    }

    /**
     * Check if cell with given coordinates is drawn
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true of cell is drawn
     */
    public boolean isDrawn(int x, int y) {
        return map[get1DIndex(x, y)];
    }

    /**
     * Set state of cell with given coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     * @param isDrawn should be drawn or not
     */
    public void setDrawn(int x, int y, boolean isDrawn) {
        map[get1DIndex(x, y)] = isDrawn;
    }

    private int get1DIndex(int x, int y) {
        while (y < 0) y += height;
        while (y >= height) y-= height;
        while (x < 0) x += width;
        while (x >= width) x-= width;
        return (y * this.height + x);
    }
}
