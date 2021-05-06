package ru.nsu.spirin.chess.view.swing;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.pieces.King;

import javax.annotation.concurrent.Immutable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    private final BoardPanel boardPanel;
    private final Controller controller;
    private final int tileID;
    private Board board;

    private static final Color lightTileColor = Color.decode("#FFFACD");
    private static final Color darkTileColor = Color.decode("#593E1A");

    public TilePanel(final Controller controller, final BoardPanel boardPanel, final int tileID) {
        super(new GridBagLayout());
        setDoubleBuffered(false);

        this.boardPanel = boardPanel;
        this.controller = controller;
        this.tileID = tileID;
        setPreferredSize(SwingView.TILE_PANEL_DIMENSION);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (!board.getCurrentPlayer().isAI()) {
                    if (isRightMouseButton(e)) {
                        boardPanel.setSourceTile(null);
                        boardPanel.setDestinationTile(null);
                        boardPanel.setHumanMovedPiece(null);
                    }
                    else if (isLeftMouseButton(e)) {
                        if (boardPanel.getSourceTile() == null) {
                            boardPanel.setSourceTile(board.getTile(tileID));
                            boardPanel.setHumanMovedPiece(boardPanel.getSourceTile().getPiece());
                            if (boardPanel.getHumanMovedPiece() == null) {
                                boardPanel.setSourceTile(null);
                            }
                        }
                        else {
                            boardPanel.setDestinationTile(board.getTile(tileID));
                            boolean execResult = controller.execute("move " + BoardUtils.getPositionAtCoordinate(boardPanel.getSourceTile().getTileCoordinate()) + " " + BoardUtils.getPositionAtCoordinate(boardPanel.getDestinationTile().getTileCoordinate()), false);
                            boardPanel.setSourceTile(null);
                            boardPanel.setDestinationTile(null);
                            boardPanel.setHumanMovedPiece(null);
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
        this.removeAll();
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
                System.out.println(this.tileID);
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
        if (boardPanel.getHumanMovedPiece() != null && boardPanel.getHumanMovedPiece().getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
            return  boardPanel.getHumanMovedPiece().calculateLegalMoves(board);
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
        if (BoardUtils.isPositionInRow(this.tileID, 1) || BoardUtils.isPositionInRow(this.tileID, 3) || BoardUtils.isPositionInRow(this.tileID, 5) || BoardUtils.isPositionInRow(this.tileID, 7)) {
            setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
        }
        else if (BoardUtils.isPositionInRow(this.tileID, 2)|| BoardUtils.isPositionInRow(this.tileID, 4) || BoardUtils.isPositionInRow(this.tileID, 6) || BoardUtils.isPositionInRow(this.tileID, 8)) {
            setBackground(this.tileID % 2 == 0 ? darkTileColor : lightTileColor);
        }
    }
}
