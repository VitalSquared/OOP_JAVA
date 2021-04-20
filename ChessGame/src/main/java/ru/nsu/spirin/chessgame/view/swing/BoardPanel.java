package ru.nsu.spirin.chessgame.view.swing;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.board.tile.Tile;
import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.pieces.Piece;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

class BoardPanel extends JPanel {
    private       Tile            sourceTile;
    private       Tile            destinationTile;
    private       Piece           humanMovedPiece;
    private final List<TilePanel> boardTiles;

    public BoardPanel(final Controller controller) {
        super(new GridLayout(8, 8));
        this.boardTiles = new ArrayList<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final TilePanel tilePanel = new TilePanel(controller, this, i);
            this.boardTiles.add(tilePanel);
            add(tilePanel);
        }
        setPreferredSize(SwingView.BOARD_PANEL_DIMENSION);
        validate();
    }

    public void drawBoard(final Board board) {
        removeAll();
        for (final TilePanel tilePanel : boardTiles) {
            tilePanel.drawTile(board);
            add(tilePanel);
        }
        validate();
        repaint();
    }

    public void setSourceTile(Tile sourceTile) {
        this.sourceTile = sourceTile;
    }

    public void setDestinationTile(Tile destinationTile) {
        this.destinationTile = destinationTile;
    }

    public void setHumanMovedPiece(Piece humanMovedPiece) {
        this.humanMovedPiece = humanMovedPiece;
    }

    public Tile getSourceTile() {
        return this.sourceTile;
    }

    public Tile getDestinationTile() {
        return this.destinationTile;
    }

    public Piece getHumanMovedPiece() {
        return this.humanMovedPiece;
    }
}
