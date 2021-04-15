package ru.nsu.spirin.chessgame.view.swing;

import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.board.tile.Tile;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.player.PlayerType;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.view.GameView;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.Observable;

public class SwingView extends Observable implements GameView {

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

    private Move computerMove;

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

        this.addObserver(new TableGameAIWatcher());

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

    private NewGameDialog getGameSetup() {
        return this.newGameDialog;
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
        preferencesMenu.add(flipBoardMenuItem);
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

    private void setupUpdate(final NewGameDialog newGameDialog) {
        setChanged();
        notifyObservers(newGameDialog);
    }

    public void updateComputerMove(final Move move) {
        this.computerMove = move;
    }

    private void moveMadeUpdate(final PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
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
            }
        }
    }

    @Override
    public void close() {
        gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
    }
}
