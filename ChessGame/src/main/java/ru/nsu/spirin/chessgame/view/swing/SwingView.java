package ru.nsu.spirin.chessgame.view.swing;

import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.board.tile.Tile;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.view.GameView;
import ru.nsu.spirin.chessgame.view.swing.dialog.AboutDialog;
import ru.nsu.spirin.chessgame.view.swing.dialog.HighScoresDialog;
import ru.nsu.spirin.chessgame.view.swing.dialog.NewGameDialog;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

public class SwingView implements GameView {

    private final JFrame gameFrame;
    private final Controller controller;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private final NewGameDialog newGameDialog;
    private final HighScoresDialog highScoresDialog;
    private final AboutDialog aboutDialog;

    private boolean pressedExit = false;
    private boolean isAIMove = false;

    public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    public static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    public static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);

    public SwingView(Controller controller) {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        this.controller = controller;
        final JMenuBar tableMenuBar = populateMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel(this, controller);

        this.newGameDialog = new NewGameDialog(this.gameFrame, true, controller);
        this.aboutDialog = new AboutDialog(this.gameFrame, true);
        this.highScoresDialog = new HighScoresDialog(this.gameFrame, true);

        this.boardDirection = BoardDirection.NORMAL;
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.setVisible(true);
    }

    public BoardDirection getBoardDirection() {
        return this.boardDirection;
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

    private JMenuBar populateMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createGameMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
        return tableMenuBar;
    }

    private JMenu createGameMenu() {
        final JMenu fileMenu = new JMenu("Game");

        final JMenuItem newGameButton = new JMenuItem("New Game");
        newGameButton.addActionListener(e -> {
            newGameDialog.promptUser();
        });
        fileMenu.add(newGameButton);

        final JMenuItem highscoreButton = new JMenuItem("High Scores");
        highscoreButton.addActionListener(e -> {
            highScoresDialog.promptUser();
        });
        fileMenu.add(highscoreButton);

        final JMenuItem aboutButton = new JMenuItem("About");
        aboutButton.addActionListener(e -> {
            aboutDialog.promptUser();
        });
        fileMenu.add(aboutButton);

        final JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(e -> {
            this.pressedExit = true;
        });
        fileMenu.add(exitButton);

        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
        });

        final JMenuItem surrenderButton = new JMenuItem("Surrender");
        surrenderButton.addActionListener(e -> {
            controller.execute("surrender", false);
        });

        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.add(surrenderButton);
        return preferencesMenu;
    }

    private JMenu createOptionsMenu() {

        final JMenu optionsMenu = new JMenu("Options");

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        setupGameMenuItem.addActionListener(e -> {
            //SwingView.get().getGameSetup().promptUser();
            //SwingView.get().setupUpdate(SwingView.get().getGameSetup());
        });
        optionsMenu.add(setupGameMenuItem);
        return optionsMenu;
    }

    @Override
    public void render(Scene scene) {
        if (pressedExit) {
            scene.destroyScene();
            return;
        }

        switch (scene.getSceneState()) {
            case MAIN_MENU -> {
                boardPanel.removeAll();
            }
            case GAME -> {
                gameHistoryPanel.redo(scene.getBoard(), scene.getMoveLog());
                takenPiecesPanel.redo(scene.getMoveLog());
                boardPanel.drawBoard(scene.getBoard());

                if (scene.getBoard().getCurrentPlayer().isAI()) {
                    boolean execResult = controller.execute("ai_move", true);
                    isAIMove = true;
                }
                else {
                    isAIMove = false;
                }
            }
        }
    }

    @Override
    public void close() {
        gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
    }

    public boolean isAIMove() {
        return this.isAIMove;
    }
}
