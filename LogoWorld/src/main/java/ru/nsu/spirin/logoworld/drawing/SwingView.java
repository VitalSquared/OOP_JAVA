package ru.nsu.spirin.logoworld.drawing;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.drawing.GraphicsView;
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

            int width = WIDTH;
            int height =  HEIGHT;

            int padding_top = TEXTURE_SIZE;
            int padding_btm = TEXTURE_SIZE;
            int padding_lft = TEXTURE_SIZE;
            int padding_rgt = TEXTURE_SIZE;

            Pair fieldSize = world.getFieldSize();

            int map_width = Math.min((width - (padding_lft + padding_rgt)) / TEXTURE_SIZE, fieldSize.getFirst());
            int map_height = Math.min((height - (padding_top + padding_btm)) / TEXTURE_SIZE, fieldSize.getSecond());

            Pair executorCoords = world.getTurtlePosition();

            int top_left_r = executorCoords.getFirst() - map_height / 2;
            int top_left_c = executorCoords.getSecond() - map_width / 2;

            for (int r = 0; r < map_height; r++) {
                for (int c = 0; c < map_width; c++) {
                    int coords_r = r + top_left_r;
                    int coords_c = c + top_left_c;
                    if (world.isCellDrawn(coords_r, coords_c)) {
                        g.setColor(Color.GREEN);
                        g.fillRect(c * TEXTURE_SIZE, r * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
                    }
                    else {
                        g.setColor(Color.BLACK);
                        g.drawRect(c * TEXTURE_SIZE, r * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
                    }
                }
            }

            int pos_r = executorCoords.getFirst();
            int pos_c = executorCoords.getSecond();
            g.setColor(Color.CYAN);
            g.fillArc((pos_c - top_left_c) * TEXTURE_SIZE, (pos_r - top_left_r) * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, 0, 360);

            g.dispose();
            bs.show();
        }
        else {
            writeInformation("You have to init the map first. Use INIT <width> <height> <x> <y>");
        }
    }
}
