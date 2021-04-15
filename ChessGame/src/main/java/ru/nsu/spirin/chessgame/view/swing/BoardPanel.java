package ru.nsu.spirin.chessgame.view.swing;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.controller.Controller;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

class BoardPanel extends JPanel {
    private final SwingView swingView;
    final List<TilePanel> boardTiles;

    public BoardPanel(final SwingView swingView, final Controller controller) {
        super(new GridLayout(8, 8));
        this.swingView = swingView;
        this.boardTiles = new ArrayList<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final TilePanel tilePanel = new TilePanel(swingView, controller, this, i);
            this.boardTiles.add(tilePanel);
            add(tilePanel);
        }
        setPreferredSize(SwingView.BOARD_PANEL_DIMENSION);
        validate();
    }

    public void drawBoard(final Board board) {
        removeAll();
        for (final TilePanel tilePanel : swingView.getBoardDirection().traverse(boardTiles)) {
            tilePanel.drawTile(board);
            add(tilePanel);
        }
        validate();
        repaint();
    }
}
