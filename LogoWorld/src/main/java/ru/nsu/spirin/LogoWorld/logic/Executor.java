package ru.nsu.spirin.LogoWorld.logic;

import ru.nsu.spirin.LogoWorld.math.Direction;
import ru.nsu.spirin.LogoWorld.math.Pair;

public class Executor {
    private int pos_r;
    private int pos_c;
    private boolean isDrawing;
    private Field field;

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
        Pair delta = Direction.convertDirectionToDelta(dir);
        this.pos_r += delta.getFirst();
        this.pos_c += delta.getSecond();
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
}
