package ru.nsu.spirin.LogoWorld.logic;

import ru.nsu.spirin.LogoWorld.math.Direction;
import ru.nsu.spirin.LogoWorld.math.Pair;

public class Executor {
    int pos_r;
    int pos_c;
    boolean isDrawing;

    public Executor() {
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
        Pair delta = Direction.dirToDelta(dir);
        this.pos_r += delta.getX();
        this.pos_c += delta.getY();
    }

    public Pair getPosition() {
        return new Pair(pos_r, pos_c);
    }
}
