package ru.nsu.spirin.chessgame.view.swing;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.player.PlayerType;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class TilePanel extends JPanel {
    private final SwingView swingView;
    private final Controller controller;
    private final int tileID;
    private Board board;

    private static final Color lightTileColor = Color.decode("#FFFACD");
    private static final Color darkTileColor = Color.decode("#593E1A");

    public TilePanel(final SwingView swingView, final Controller controller, final BoardPanel boardPanel, final int tileID) {
        super(new GridBagLayout());
        this.swingView = swingView;
        this.controller = controller;
        this.tileID = tileID;
        setPreferredSize(SwingView.TILE_PANEL_DIMENSION);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (!swingView.isAIMove()) {
                    if (isRightMouseButton(e)) {
                        swingView.setSourceTile(null);
                        swingView.setDestinationTile(null);
                        swingView.setHumanMovedPiece(null);
                    }
                    else if (isLeftMouseButton(e)) {
                        if (swingView.getSourceTile() == null) {
                            swingView.setSourceTile(board.getTile(tileID));
                            swingView.setHumanMovedPiece(swingView.getSourceTile().getPiece());
                            if (swingView.getHumanMovedPiece() == null) {
                                swingView.setSourceTile(null);
                            }
                        }
                        else {
                            swingView.setDestinationTile(board.getTile(tileID));
                            boolean execResult = controller.execute("move " + BoardUtils.getPositionAtCoordinate(swingView.getSourceTile().getTileCoordinate()) + " " + BoardUtils.getPositionAtCoordinate(swingView.getDestinationTile().getTileCoordinate()), false);
                            swingView.setSourceTile(null);
                            swingView.setDestinationTile(null);
                            swingView.setHumanMovedPiece(null);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {

            }

            @Override
            public void mouseReleased(final MouseEvent e) {

            }

            @Override
            public void mouseEntered(final MouseEvent e) {

            }

            @Override
            public void mouseExited(final MouseEvent e) {

            }
        });

        validate();
    }

    public void drawTile(final Board board) {
        this.board = board;
        assignTileColor();
        assignTilePieceIcon(board);
        highlightLegalMoves(board);
        validate();
        repaint();
    }

    private void highlightLegalMoves(final Board board) {
        if (true) {
            for (final Move move : pieceLegalMoves(board)) {
                if (move.getDestinationCoordinate() == this.tileID) {
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File("textures/green_dot.png")))));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Collection<Move> pieceLegalMoves(final Board board) {
        if (swingView.getHumanMovedPiece() != null && swingView.getHumanMovedPiece().getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
            return swingView.getHumanMovedPiece().calculateLegalMoves(board);
        }
        return Collections.emptyList();
    }

    private void assignTilePieceIcon(final Board board) {
        this.removeAll();
        if (board.getTile(this.tileID).isTileOccupied()) {
            String pieceIconPath = "textures/";
            try {
                final BufferedImage image = ImageIO.read(new File(pieceIconPath + board.getTile(this.tileID).getPiece().getPieceAlliance().toString().charAt(0) + board.getTile(this.tileID).toString() + ".gif"));
                add(new JLabel(new ImageIcon(image)));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void assignTileColor() {
        if (BoardUtils.FIRST_ROW[this.tileID] || BoardUtils.THIRD_ROW[this.tileID] || BoardUtils.FIFTH_ROW[this.tileID] || BoardUtils.SEVENTH_ROW[this.tileID]) {
            setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
        }
        else if (BoardUtils.SECOND_ROW[this.tileID] || BoardUtils.FOURTH_ROW[this.tileID] || BoardUtils.SIXTH_ROW[this.tileID] || BoardUtils.EIGHTH_ROW[this.tileID]) {
            setBackground(this.tileID % 2 == 0 ? darkTileColor : lightTileColor);
        }
    }
}
