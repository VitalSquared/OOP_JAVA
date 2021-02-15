package ru.nsu.spirin.logoworld.logic;

import ru.nsu.spirin.logoworld.math.Direction;
import ru.nsu.spirin.logoworld.math.Pair;

public class Turtle {
    private int pos_r;
    private int pos_c;
    private boolean isDrawing;

    public Turtle() {
        this.isDrawing = false;
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
}
