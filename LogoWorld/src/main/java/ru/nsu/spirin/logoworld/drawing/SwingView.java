package ru.nsu.spirin.logoworld.drawing;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.exceptions.RenderException;
import ru.nsu.spirin.logoworld.logic.World;
import ru.nsu.spirin.logoworld.math.Pair;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class SwingView extends Canvas implements GraphicsView {
    private static final Logger logger = Logger.getLogger(SwingView.class);

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final int TEXTURE_SIZE = 50;
    private JFrame frame;

    public SwingView() {
        logger.debug("Swing View initialization.");

        frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        frame.add(this);
        frame.setTitle("Logo World");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void writeInformation(String info) {
    }

    @Override
    public boolean getContinuationSignal() {
        return true;
    }

    @Override
    public void render(World world) throws RenderException {
        if (world.isValid()) {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null)
            {
                this.createBufferStrategy(3);
                return;
            }

            Graphics g = bs.getDrawGraphics();

            writeInformation("");

            g.clearRect(0, 0, WIDTH, HEIGHT);

            int paddingTop = TEXTURE_SIZE;
            int paddingBottom = TEXTURE_SIZE;
            int paddingLeft = TEXTURE_SIZE;
            int paddingRight = TEXTURE_SIZE;

            Pair fieldSize = world.getFieldSize();

            int map_width = Math.min((WIDTH - (paddingLeft + paddingRight)) / TEXTURE_SIZE, fieldSize.getFirst());
            int map_height = Math.min((HEIGHT - (paddingTop + paddingBottom)) / TEXTURE_SIZE, fieldSize.getSecond());

            Pair turtlePos = world.getTurtlePosition();

            int topLeftX = turtlePos.getFirst() - map_width / 2;
            int topLeftY = turtlePos.getSecond() - map_height / 2;

            for (int y = 0; y < map_height; y++) {
                for (int x = 0; x < map_width; x++) {
                    int xPos = x + topLeftX;
                    int yPos = y + topLeftY;
                    boolean isDrawn = world.isCellDrawn(xPos, yPos);
                    g.setColor(isDrawn ? Color.GREEN : Color.BLACK);
                    if (isDrawn) {
                        g.fillRect(paddingLeft + x * TEXTURE_SIZE, paddingTop + y * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
                    }
                    else {
                        g.drawRect(paddingLeft + x * TEXTURE_SIZE, paddingTop + y * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
                    }
                }
            }

            int turtleX = turtlePos.getFirst();
            int turtleY = turtlePos.getSecond();
            g.setColor(Color.CYAN);
            g.fillArc(paddingLeft + (turtleX - topLeftX) * TEXTURE_SIZE, paddingTop + (turtleY - topLeftY) * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, 0, 360);

            g.dispose();
            bs.show();
        }
        else {
            writeInformation("You have to init the map first. Use INIT <width> <height> <x> <y>");
        }
    }
}
