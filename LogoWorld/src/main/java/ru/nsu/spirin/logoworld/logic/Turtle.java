package ru.nsu.spirin.logoworld.logic;

import ru.nsu.spirin.logoworld.math.Direction;
import ru.nsu.spirin.logoworld.math.Pair;

public class Turtle {
    private int xPos;
    private int yPos;
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

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void move(Direction dir) {
        Pair delta = Direction.convertDirectionToDelta(dir);
        this.xPos += delta.getFirst();
        this.yPos += delta.getSecond();
    }

    public Pair getPosition() {
        return new Pair(xPos, yPos);
    }
}
