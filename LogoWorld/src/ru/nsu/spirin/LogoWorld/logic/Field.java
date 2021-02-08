package ru.nsu.spirin.LogoWorld.logic;

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

    public boolean isDrawn(int r, int c) {
        return map[normalizeRow(r) * this.height + normalizeCol(c)];
    }

    public void setDrawn(int r, int c, boolean isDrawn) {
        map[r * this.height + c] = isDrawn;
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
