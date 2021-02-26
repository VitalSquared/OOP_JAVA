package ru.nsu.spirin.logoworld.logic;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.math.Direction;
import ru.nsu.spirin.logoworld.math.Pair;

public class World {
    private static final Logger logger = Logger.getLogger(World.class);

    private final Turtle turtle;
    private final Field field;

    /**
     * Create world, which contains field and turtle
     */
    public World() {
        logger.debug("World initialization.");
        this.field = new Field();
        this.turtle = new Turtle();
    }

    /**
     * Init world parameters.
     *
     * @param width  Field width
     * @param height Field height
     * @param x      Turtle x-position
     * @param y      Turtle y-position
     */
    public void initWorld(int width, int height, int x, int y) {
        logger.debug("Initializing world parameters: " + width + " " + height + " " + x + " " + y);
        field.setSize(width, height);
        turtle.setPosition(x, y);
    }

    /**
     * Check if turtle is currently drawing
     *
     * @return true if turtle is drawing
     * @see World#setIsTurtleDrawing(boolean)
     */
    public boolean getIsTurtleDrawing() {
        return turtle.getIsDrawing();
    }

    /**
     * Set turtle drawing state
     *
     * @param isDrawing whether turtle should draw or not
     * @see World#getIsTurtleDrawing() (boolean)
     */
    public void setIsTurtleDrawing(boolean isDrawing) {
        logger.debug("Switch turtle drawing mode");
        turtle.setIsDrawing(isDrawing);
        update();
    }

    /**
     * Set new position of turtle
     *
     * @param x x-position
     * @param y y-position
     */
    public void setTurtlePosition(int x, int y) {
        logger.debug("Set turtle position: " + x + " " + y);
        turtle.setPosition(x, y);
        update();
    }

    /**
     * Move turtle in direction
     *
     * @param dir direction
     */
    public void moveTurtle(Direction dir) {
        logger.debug("Move turtle in direction " + dir.toString());
        turtle.move(dir);
        update();
    }

    /**
     * Check if world is valid
     *
     * @return true if turtle and field have been initialized
     */
    public boolean isValid() {
        Pair size = field.getSize();
        return (size.getFirst() != 0 && size.getSecond() != 0);
    }

    /**
     * Get turtle position
     *
     * @return (x, y) - turtle position
     */
    public Pair getTurtlePosition() {
        return turtle.getPosition();
    }

    /**
     * Get field size
     *
     * @return (width, height) - field size
     */
    public Pair getFieldSize() {
        return field.getSize();
    }

    /**
     * Check if cell with given coordinates is drawn by turtle
     *
     * @param x x-position
     * @param y y-position
     * @return true if cell is drawn
     */
    public boolean isCellDrawn(int x, int y) {
        return field.isDrawn(x, y);
    }

    private void update() {
        Pair pos = turtle.getPosition();
        Pair size = field.getSize();
        int x = pos.getFirst(), y = pos.getSecond();

        while (y < 0) y += size.getSecond();
        while (y >= size.getSecond()) y -= size.getSecond();

        while (x < 0) x += size.getFirst();
        while (x >= size.getFirst()) x -= size.getFirst();

        turtle.setPosition(x, y);

        if (turtle.getIsDrawing()) {
            field.setDrawn(x, y, true);
        }
    }
}
