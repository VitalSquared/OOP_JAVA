package ru.nsu.spirin.logoworld.logic;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.math.Direction;
import ru.nsu.spirin.logoworld.math.Pair;

public class World {
    private static final Logger logger = Logger.getLogger(World.class);

    private final Turtle turtle;
    private final Field field;

    public World() {
        logger.debug("World started initialization.");
        this.field = new Field();
        this.turtle = new Turtle();
        logger.debug("World finished initialization.");
    }

    public void initWorld(int width, int height, int x, int y) {
        logger.debug("Initializing world parameters");
        field.setSize(width, height);
        turtle.setPosition(y, x);
    }

    public boolean getIsTurtleDrawing() {
        return turtle.getIsDrawing();
    }

    public void setIsTurtleDrawing(boolean isDrawing) {
        logger.debug("Switch turtle drawing mode");
        turtle.setIsDrawing(isDrawing);
        updateExecutorAndField();
    }

    public void setTurtlePosition(int pos_r, int pos_c) {
        logger.debug("Set turtle position: " + pos_r + " " + pos_c);
        turtle.setPosition(pos_r, pos_c);
        updateExecutorAndField();
    }

    public void moveTurtle(Direction dir) {
        logger.debug("Move turtle in direction " + dir.toString());
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
}
