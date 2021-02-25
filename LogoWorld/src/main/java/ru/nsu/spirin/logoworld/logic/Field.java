package ru.nsu.spirin.logoworld.logic;

public class Field {
    private int width;
    private int height;

    private boolean[] map;

    public Field() {
        this.width = 0;
        this.height = 0;
        map = null;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        map = new boolean[width * height];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isDrawn(int x, int y) {
        return map[normalizeRow(y) * this.height + normalizeCol(x)];
    }

    public void setDrawn(int x, int y, boolean isDrawn) {
        map[normalizeRow(y)  * this.height + normalizeCol(x)] = isDrawn;
    }

    private int normalizeRow(int r) {
        while (r < 0) r += this.height;
        while (r >= this.height) r-= this.height;
        return r;
    }

    private int normalizeCol(int c) {
        while (c < 0) c += this.width;
        while (c >= this.width) c-= this.width;
        return c;
    }
}
