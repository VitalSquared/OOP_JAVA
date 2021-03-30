package ru.nsu.spirin.battlecity.view;

import ru.nsu.spirin.battlecity.controller.TankController;
import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.Bullet;
import ru.nsu.spirin.battlecity.model.Entity;
import ru.nsu.spirin.battlecity.model.EntityMovable;
import ru.nsu.spirin.battlecity.model.World;
import ru.nsu.spirin.battlecity.model.map.GridTile;
import ru.nsu.spirin.battlecity.model.tank.PlayerTank;
import ru.nsu.spirin.battlecity.model.tank.Tank;
import ru.nsu.spirin.battlecity.model.tiles.TileBrick;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SwingView extends Canvas implements GameView {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final int TEXTURE_SIZE = 50;
    private JFrame frame;

    private final Image playerTankImage;
    private final Image bricksImage;
    private final Image unknownImage;
    private final Image bulletImage;

    public SwingView(TankController tankController) throws IOException {
        frame = new JFrame();

        playerTankImage = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("textures/T_Tank.png")));
        bricksImage = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("textures/T_Bricks.png")));
        unknownImage = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("textures/T_Unknown.png")));
        bulletImage = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("textures/T_Bullet.png")));

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
        addKeyListener(new InputHandler(tankController));

        frame.add(this);
        frame.setTitle("Battle City");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void render(World world) {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        List<Entity> entityList = world.getEntityList();
        for (var entity : entityList) {
            Image imageBuf = unknownImage;
            Point2D pos = entity.getPosition();
            Point2D size = entity.getSize();
            if (entity instanceof EntityMovable) {
                if (entity instanceof Bullet) {
                    imageBuf = bulletImage;
                }

                if (entity instanceof PlayerTank) {
                    imageBuf = playerTankImage;
                }

                Graphics2D g2d = (Graphics2D) g;
                AffineTransform tr = new AffineTransform();
                tr.translate(pos.getX(), pos.getY());
                tr.rotate(
                        Math.toRadians (Direction.convertDirectionToAngleDegrees(((EntityMovable) entity).getDirection())),
                        TEXTURE_SIZE / 2.0,
                        TEXTURE_SIZE / 2.0
                );
                g2d.drawImage(imageBuf, tr, (img, infoflags, x, y, width, height) -> true);
            }
            else {
                if (entity instanceof TileBrick) {
                    imageBuf = bricksImage;
                }
                g.drawImage(imageBuf, pos.getX(), pos.getY(), size.getX(), size.getY(), (img, infoflags, x1, y1, width, height) -> true);
            }

        }

        g.dispose();
        bs.show();
    }
}

class InputHandler implements KeyListener {
    private final TankController tankController;

    public InputHandler(TankController tankController) {
        this.tankController = tankController;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> tankController.moveUp();
            case KeyEvent.VK_S -> tankController.moveDown();
            case KeyEvent.VK_A -> tankController.moveLeft();
            case KeyEvent.VK_D -> tankController.moveRight();
            case KeyEvent.VK_SPACE -> tankController.shoot();
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
