package ru.nsu.spirin.chessgame.view.swing;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.view.GameView;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table implements GameView {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static final Color lightTileColor = Color.ORANGE;
    private static final Color darkTileColor = Color.DARK_GRAY;

    public Table() {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = populateMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar populateMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up pgn file!");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitButtonItem = new JMenuItem("Exit");
        exitButtonItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitButtonItem);

        return fileMenu;
    }

    @Override
    public void render(Board board) {

    }

    @Override
    public void close() {

    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileID;

        TilePanel(final BoardPanel boardPanel,
                  final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileID).isTileOccupied()) {
                String pieceIconPath = "textures/";
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0, 1) +
                            board.getTile(this.tileID).toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.FIRST_ROW[this.tileID] ||
                    BoardUtils.THIRD_ROW[this.tileID] ||
                    BoardUtils.FIFTH_ROW[this.tileID] ||
                    BoardUtils.SEVENTH_ROW[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? lightTileColor: darkTileColor);
            }
            else if (BoardUtils.SECOND_ROW[this.tileID] ||
                    BoardUtils.FOURTH_ROW[this.tileID] ||
                    BoardUtils.SIXTH_ROW[this.tileID] ||
                    BoardUtils.EIGHTH_ROW[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? darkTileColor: lightTileColor);
            }
        }
    }
}
