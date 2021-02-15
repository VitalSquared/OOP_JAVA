package ru.nsu.spirin.logoworld.logic;

import ru.nsu.spirin.logoworld.commands.Command;
import ru.nsu.spirin.logoworld.math.Direction;
import ru.nsu.spirin.logoworld.math.Pair;

public class World {
    private final Turtle turtle;
    private final Field field;

    public World() {
        this.field = new Field();
        this.turtle = new Turtle();
    }

    public void initWorld(Object sender, int width, int height, int x, int y) {
        if (!validateSender(sender)) return;
        field.setSize(width, height);
        turtle.setPosition(y, x);
    }

    public boolean getIsTurtleDrawing() {
        return turtle.getIsDrawing();
    }

    public void setIsTurtleDrawing(Object sender, boolean isDrawing) {
        if (!validateSender(sender)) return;
        turtle.setIsDrawing(isDrawing);
        updateExecutorAndField();
    }

    public void setTurtlePosition(Object sender, int pos_r, int pos_c) {
        if (!validateSender(sender)) return;
        turtle.setPosition(pos_r, pos_c);
        updateExecutorAndField();
    }

    public void moveTurtle(Object sender, Direction dir) {
        if (!validateSender(sender)) return;
        turtle.move(dir);
        updateExecutorAndField();
    }

    public boolean isValid() {
        return field.getWidth() != 0 && field.getHeight() != 0;
    }

    public Pair getTurtlePosition() {
        return turtle.getPosition();
    }

    public Pair getFieldSize() {
        return new Pair(field.getWidth(), field.getHeight());
    }

    public boolean isCellDrawn(int r, int c) {
        return field.isDrawn(r, c);
    }

    private void updateExecutorAndField() {
        Pair pos = turtle.getPosition();
        int r = pos.getFirst(), c = pos.getSecond();

        while (r < 0) r += field.getHeight();
        while (r >= field.getHeight()) r -= field.getHeight();

        while (c < 0) c += field.getWidth();
        while (c >= field.getWidth()) c -= field.getWidth();

        turtle.setPosition(r, c);

        if (turtle.getIsDrawing()) {
            field.setDrawn(r, c, true);
        }
    }

    private boolean validateSender(Object sender) {
        return sender instanceof Command;
    }
}
