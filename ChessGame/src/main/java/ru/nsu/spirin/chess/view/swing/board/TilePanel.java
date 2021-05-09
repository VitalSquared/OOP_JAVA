package ru.nsu.spirin.chess.view.swing.board;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.view.swing.SwingView;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

class TilePanel extends JPanel {
    private final BoardPanel boardPanel;
    private final int normalTileID;
    private int tileID;
    private final Scene scene;

    private static final Color lightTileColor = Color.decode("#FFFACD");
    private static final Color darkTileColor = Color.decode("#593E1A");

    public TilePanel(Scene scene, Controller controller, BoardPanel boardPanel, int tileID) {
        super(new GridBagLayout());
        setDoubleBuffered(false);

        this.scene = scene;
        this.boardPanel = boardPanel;
        this.normalTileID = tileID;
        this.tileID = tileID;
        setPreferredSize(SwingView.TILE_PANEL_DIMENSION);

        addMouseListener(new TileMouseListener(this, controller));

        validate();
    }

    public void drawTile(boolean isInverted) {
        this.tileID = isInverted ? BoardUtils.TOTAL_NUMBER_OF_TILES - 1 - normalTileID : normalTileID;
        this.removeAll();
        assignTileColor();
        assignTilePieceIcon(scene.getActiveGame().getBoard());
        highlightLegalMoves(scene.getActiveGame().getBoard());
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
        if (boardPanel.getHumanMovedPiece() != null && boardPanel.getHumanMovedPiece().getAlliance() == board.getCurrentPlayer().getAlliance()) {
            Collection<Move> moves = new ArrayList<>();
            for (var move : board.getCurrentPlayer().getLegalMoves()) {
                if (move.getMovedPiece() == boardPanel.getHumanMovedPiece()) {
                    moves.add(move);
                }
            }
            return moves;
        }
        return Collections.emptyList();
    }

    private void assignTilePieceIcon(final Board board) {
        this.removeAll();
        if (board.getTile(this.tileID).isTileOccupied()) {
            String pieceIconPath = "textures/";
            try {
                final BufferedImage image = ImageIO.read(new File(pieceIconPath + board.getTile(this.tileID).getPiece().getAlliance().toString().charAt(0) + board.getTile(this.tileID).toString() + ".gif"));
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

    private class TileMouseListener implements MouseListener {
        private final TilePanel tilePanel;
        private final Controller controller;

        public TileMouseListener(TilePanel tilePanel, Controller controller) {
            this.tilePanel = tilePanel;
            this.controller = controller;
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            if (scene.getActiveGame().getPlayerAlliance() == scene.getActiveGame().getBoard().getCurrentPlayer().getAlliance() && !BoardUtils.isEndGame(scene.getActiveGame().getBoard())) {
                if (isRightMouseButton(e)) {
                    boardPanel.setSourceTile(null);
                    boardPanel.setDestinationTile(null);
                    boardPanel.setHumanMovedPiece(null);
                }
                else if (isLeftMouseButton(e)) {
                    if (boardPanel.getSourceTile() == null) {
                        boardPanel.setSourceTile(scene.getActiveGame().getBoard().getTile(tilePanel.tileID));
                        boardPanel.setHumanMovedPiece(boardPanel.getSourceTile().getPiece());
                        if (boardPanel.getHumanMovedPiece() == null) {
                            boardPanel.setSourceTile(null);
                        }
                    }
                    else {
                        boardPanel.setDestinationTile(scene.getActiveGame().getBoard().getTile(tilePanel.tileID));
                        boolean execResult = controller.execute("move " + BoardUtils.getPositionAtCoordinate(boardPanel.getSourceTile().getCoordinate()) +
                                                                " " + BoardUtils.getPositionAtCoordinate(boardPanel.getDestinationTile().getCoordinate()) + " " + scene.getActiveGame().getPlayerAlliance().toString(), false);
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
    }
}
