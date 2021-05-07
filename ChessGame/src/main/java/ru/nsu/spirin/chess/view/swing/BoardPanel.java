package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.board.tile.Tile;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.scene.Scene;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

class BoardPanel extends JPanel {
    private       Tile            sourceTile;
    private       Tile            destinationTile;
    private       Piece           humanMovedPiece;
    private final List<TilePanel> boardTiles;
    private       Scene           scene;

    private final GameHistoryPanel gameHistoryPanel;
    private final PlayerInfoPanel  whitePlayerInfoPanel;
    private final PlayerInfoPanel  blackPlayerInfoPanel;

    public BoardPanel(final Controller controller) {
        super(new BorderLayout());
        setDoubleBuffered(false);

        JPanel tilesPanel = new JPanel(new GridLayout(8, 8));

        this.gameHistoryPanel = new GameHistoryPanel();
        this.whitePlayerInfoPanel = new PlayerInfoPanel(true);
        this.blackPlayerInfoPanel = new PlayerInfoPanel(false);

        add(this.blackPlayerInfoPanel, BorderLayout.NORTH);
        add(this.whitePlayerInfoPanel, BorderLayout.SOUTH);
        add(this.gameHistoryPanel, BorderLayout.EAST);
        add(tilesPanel, BorderLayout.CENTER);

        this.boardTiles = new ArrayList<>();
        for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
            final TilePanel tilePanel = new TilePanel(controller, this, i);
            this.boardTiles.add(tilePanel);
            tilesPanel.add(tilePanel);
        }
        setPreferredSize(SwingView.BOARD_PANEL_DIMENSION);
        validate();

        setVisible(false);

        new Thread(() -> {
            while (true) {
                if (isVisible()) {
                    try {
                        drawBoard(scene.getBoard());
                        gameHistoryPanel.redo(scene.getBoard(), scene.getMoveLog());
                        whitePlayerInfoPanel.redo(scene.getBoard().getWhitePlayer().getPlayerName(), scene.getMoveLog());
                        blackPlayerInfoPanel.redo(scene.getBoard().getBlackPlayer().getPlayerName(), scene.getMoveLog());
                    }
                    catch (Exception ignored) {}
                }
                try {
                    Thread.sleep(100);
                }
                catch (Exception ignored) {}
            }
        }).start();
    }

    public void updatePanel(Scene scene) {
        this.scene = scene;
    }

    private void drawBoard(final Board board) {
        for (final TilePanel tilePanel : boardTiles) {
            tilePanel.drawTile(board);
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
