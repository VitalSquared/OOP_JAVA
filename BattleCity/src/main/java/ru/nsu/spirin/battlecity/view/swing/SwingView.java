package ru.nsu.spirin.battlecity.view.swing;

import ru.nsu.spirin.battlecity.controller.Controller;
import ru.nsu.spirin.battlecity.exceptions.FactoryException;
import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.scene.Scene;
import ru.nsu.spirin.battlecity.model.scene.battle.Bullet;
import ru.nsu.spirin.battlecity.model.scene.Entity;
import ru.nsu.spirin.battlecity.model.scene.battle.EntityMovable;
import ru.nsu.spirin.battlecity.model.scene.battle.tank.EnemyTank;
import ru.nsu.spirin.battlecity.model.scene.battle.tank.PlayerTank;
import ru.nsu.spirin.battlecity.model.scene.battle.tile.Tile;
import ru.nsu.spirin.battlecity.model.scene.mainmenu.infocard.InfoCard;
import ru.nsu.spirin.battlecity.model.scene.mainmenu.selectable.MenuSelectable;
import ru.nsu.spirin.battlecity.view.GameView;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class SwingView extends Canvas implements GameView {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private JFrame frame;
    private final ImageFactory imageFactory;

    public SwingView(Controller controller) throws IOException {
        frame = new JFrame();

        imageFactory = new ImageFactory();

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
        addKeyListener(new SwingInputHandler(controller));

        frame.add(this);
        frame.setTitle("Battle City");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void render(Scene scene) throws FactoryException {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        List<Entity> entityList = scene.getEntityList();
        for (var entity : entityList) {
            BufferedImage imageBuf = imageFactory.getImage("UNKNOWN");
            Point2D pos = entity.getPosition();
            Point2D size = entity.getSize();
            if (entity instanceof EntityMovable) {
                if (entity instanceof Bullet) {
                    imageBuf = imageFactory.getImage("BULLET");
                }

                if (entity instanceof PlayerTank) {
                    imageBuf = imageFactory.getImage("TANK_PLAYER");
                }

                if (entity instanceof EnemyTank) {
                    imageBuf = imageFactory.getImage("TANK_ENEMY");
                }

                Graphics2D g2d = (Graphics2D) g;
                AffineTransform tr = new AffineTransform();
                tr.translate(pos.getX(), pos.getY());
                tr.rotate(
                        Math.toRadians(Direction.convertDirectionToAngleDegrees(((EntityMovable)entity).getDirection())),
                        size.getX() / 2.0,
                        size.getY() / 2.0
                );
                tr.scale(1.0 * size.getX() / imageBuf.getWidth(), 1.0 * size.getY() / imageBuf.getHeight());
                g2d.drawImage(imageBuf, tr, (img, infoflags, x, y, width, height) -> true);
            }
            else {
                if (entity instanceof Tile) {
                    imageBuf = imageFactory.getImage("TILE_" + ((Tile) entity).getTileType().toString());
                }
                else if (entity instanceof InfoCard) {
                    imageBuf = imageFactory.getImage("MENU_" + ((InfoCard) entity).getInfoCardType().toString());
                }
                else if (entity instanceof MenuSelectable) {
                    imageBuf = imageFactory.getImage("MENU_" + ((MenuSelectable) entity).getSelectableType().toString());
                }

                if (imageBuf == null) {
                    imageBuf = imageFactory.getImage("UNKNOWN");
                }
                g.drawImage(imageBuf, pos.getX(), pos.getY(), size.getX(), size.getY(), (img, infoflags, x1, y1, width, height) -> true);


                if (entity instanceof MenuSelectable) {
                    if (((MenuSelectable) entity).isSelected()) {
                        imageBuf = imageFactory.getImage("TANK_PLAYER");
                        g.drawImage(imageBuf, pos.getX() - (int) (size.getY() * 1.5), pos.getY(), size.getY(), size.getY(), (img, infoflags, x1, y1, width, height) -> true);
                    }
                }
            }
        }

        g.dispose();
        bs.show();
    }

    @Override
    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
