package ru.nsu.spirin.chess.view.swing.board;

import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.board.tile.Tile;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.factory.Factory;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.view.GameView;
import ru.nsu.spirin.chess.view.swing.SwingView;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {
    private Tile  sourceTile;
    private Tile  destinationTile;
    private       Piece humanMovedPiece;
    private final Scene scene;

    private final List<TilePanel>        boardTiles;
    private final List<TileCaptionPanel> tileCaptions;

    private final GameHistoryPanel gameHistoryPanel;
    private final PlayerInfoPanel  topPlayerInfo;
    private final PlayerInfoPanel  bottomPlayerInfo;
    private final GameResultsPanel gameResultsPanel;


    public BoardPanel(GameView swingView, Scene scene, Controller controller, Factory<BufferedImage> imageFactory) {
        super(new BorderLayout());
        setDoubleBuffered(false);

        this.scene = scene;

        JPanel tilesPanel = new JPanel(new GridLayout(10, 10));

        this.gameHistoryPanel = new GameHistoryPanel();
        this.topPlayerInfo = new PlayerInfoPanel(imageFactory);
        this.bottomPlayerInfo = new PlayerInfoPanel(imageFactory);
        this.gameResultsPanel = new GameResultsPanel(swingView, scene, controller);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.add(this.bottomPlayerInfo);
        bottomPanel.add(gameResultsPanel);

        add(this.topPlayerInfo, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(this.gameHistoryPanel, BorderLayout.EAST);
        add(tilesPanel, BorderLayout.CENTER);

        this.boardTiles = new ArrayList<>();
        this.tileCaptions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TileCaptionPanel tileCaption = new TileCaptionPanel(i == 0 || i == 9, true, (char)('a' + i - 1), 0);
            this.tileCaptions.add(tileCaption);
            tilesPanel.add(tileCaption);
        }
        for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
            if (i % 8 == 0) {
                TileCaptionPanel tileCaption = new TileCaptionPanel(false,false, 'a', 8 - i / 8);
                this.tileCaptions.add(tileCaption);
                tilesPanel.add(tileCaption);
            }
            TilePanel tilePanel = new TilePanel(scene, controller, this, i, imageFactory);
            this.boardTiles.add(tilePanel);
            tilesPanel.add(tilePanel);
            if (i % 8 == 7) {
                TileCaptionPanel tileCaption = new TileCaptionPanel(false,false, 'a', 8 - i / 8);
                this.tileCaptions.add(tileCaption);
                tilesPanel.add(tileCaption);
            }
        }
        for (int i = 0; i < 10; i++) {
            TileCaptionPanel tileCaption = new TileCaptionPanel(i == 0 || i == 9,true, (char)('a' + i - 1), 0);
            this.tileCaptions.add(tileCaption);
            tilesPanel.add(tileCaption);
        }
        setPreferredSize(SwingView.BOARD_PANEL_DIMENSION);
        validate();

        setVisible(false);
    }

    public void updatePanel() {
        boolean exception = false;
        try {
            drawBoard();
        }
        catch (Exception e) {
            exception = true;
        }

        try {
            gameResultsPanel.updatePanel();
        }
        catch (Exception e) {
            exception = true;
        }

        try {
            gameHistoryPanel.updatePanel(scene.getActiveGame().getMoveLog());
        }
        catch (Exception e) {
            exception = true;
        }

        try {
            topPlayerInfo.setIsWhite(scene.getActiveGame().getPlayerAlliance().isBlack());
            topPlayerInfo.updatePanel(scene, scene.getActiveGame().getOpponentName(), scene.getActiveGame().getMoveLog());
        }
        catch (Exception e) {
            exception = true;
        }

        try {
            bottomPlayerInfo.setIsWhite(scene.getActiveGame().getPlayerAlliance().isWhite());
            bottomPlayerInfo.updatePanel(scene, scene.getActiveGame().getPlayerName(), scene.getActiveGame().getMoveLog());
        }
        catch (Exception e) {
            exception = true;
        }

        if (exception && !BoardUtils.isEndGame(scene.getActiveGame().getBoard())) {
            updatePanel();
        }
    }

    private void drawBoard() {
        for (TilePanel tilePanel : boardTiles) {
            tilePanel.updatePanel(scene.getActiveGame().getPlayerAlliance().isBlack());
        }
        for (TileCaptionPanel tileCaption : tileCaptions) {
            tileCaption.updatePanel(scene.getActiveGame().getPlayerAlliance().isBlack());
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
