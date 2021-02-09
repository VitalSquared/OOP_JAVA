package ru.nsu.spirin.LogoWorld.logic;

import ru.nsu.spirin.LogoWorld.math.Direction;
import ru.nsu.spirin.LogoWorld.math.Pair;

public class Executor {
    int pos_r;
    int pos_c;
    boolean isDrawing;
    Field field;

    public Executor(Field field) {
        this.isDrawing = false;
        this.field = field;
    }

    public void initField(int width, int height, int x, int y) {
        field.setSize(width, height);
        setPosition(y, x);
    }

    public boolean getIsDrawing() {
        return isDrawing;
    }

    public void setIsDrawing(boolean isDrawing) {
        this.isDrawing = isDrawing;
    }

    public void setPosition(int pos_r, int pos_c) {
        this.pos_r = pos_r;
        this.pos_c = pos_c;
    }

    public void move(Direction dir) {
        if (this.isDrawing) {
            field.setDrawn(this.pos_r, this.pos_c, true);
        }
        Pair delta = Direction.dirToDelta(dir);
        this.pos_r += delta.getX();
        this.pos_c += delta.getY();
        if (this.isDrawing) {
            field.setDrawn(this.pos_r, this.pos_c, true);
        }

        normalizeCoords();
    }

    public Pair getPosition() {
        return new Pair(pos_r, pos_c);
    }

    public boolean isValid() {
        return field.getWidth() != 0 && field.getHeight() != 0;
    }

    public Field getField() {
        return field;
    }

    private void normalizeCoords() {
        while (pos_r < 0) pos_r += field.getHeight();
        while (pos_r >= field.getHeight()) pos_r -= field.getHeight();

        while (pos_c < 0) pos_c += field.getWidth();
        while (pos_c >= field.getWidth()) pos_c -= field.getWidth();
    }
}
