package ru.nsu.spirin.chessgame.view.swing;

import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.view.GameView;
import ru.nsu.spirin.chessgame.view.swing.dialog.AboutDialog;
import ru.nsu.spirin.chessgame.view.swing.dialog.GameResultDialog;
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

public final class SwingView implements GameView {

    private final JFrame           gameFrame;
    private final Controller       controller;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel       boardPanel;

    private final NewGameDialog    newGameDialog;
    private final HighScoresDialog highScoresDialog;
    private final AboutDialog      aboutDialog;
    private final GameResultDialog gameResultDialog;

    private boolean pressedExit = false;

    private JMenu mainMenu;
    private JMenu gameMenu;

    public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    public static final Dimension TILE_PANEL_DIMENSION  = new Dimension(10, 10);
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
        this.boardPanel = new BoardPanel(controller);

        this.newGameDialog = new NewGameDialog(this.gameFrame, true, controller);
        this.aboutDialog = new AboutDialog(this.gameFrame, true);
        this.highScoresDialog = new HighScoresDialog(this.gameFrame, true);
        this.gameResultDialog = new GameResultDialog(this.gameFrame, true);

        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar populateMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createMainMenu());
        tableMenuBar.add(createGameMenu());
        return tableMenuBar;
    }

    private JMenu createMainMenu() {
        this.mainMenu = new JMenu("Main Menu");

        final JMenuItem newGameButton = new JMenuItem("New Game");
        newGameButton.addActionListener(e -> newGameDialog.promptUser());
        mainMenu.add(newGameButton);

        final JMenuItem highscoreButton = new JMenuItem("High Scores");
        highscoreButton.addActionListener(e -> highScoresDialog.promptUser());
        mainMenu.add(highscoreButton);

        final JMenuItem aboutButton = new JMenuItem("About");
        aboutButton.addActionListener(e -> aboutDialog.promptUser());
        mainMenu.add(aboutButton);

        final JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(e -> this.pressedExit = true);
        mainMenu.add(exitButton);

        return mainMenu;
    }

    private JMenu createGameMenu() {
        this.gameMenu = new JMenu("Game");
        final JMenuItem surrenderButton = new JMenuItem("Surrender");
        surrenderButton.addActionListener(e -> controller.execute("surrender", false));

        gameMenu.add(surrenderButton);
        return gameMenu;
    }

    @Override
    public void render(Scene scene) {
        if (pressedExit) {
            scene.destroyScene();
            return;
        }

        switch (scene.getSceneState()) {
            case MAIN_MENU -> {
                mainMenu.setVisible(true);
                gameMenu.setVisible(false);
                gameHistoryPanel.setVisible(false);
                boardPanel.setVisible(false);
                takenPiecesPanel.setVisible(false);
            }
            case GAME -> {
                mainMenu.setVisible(false);
                gameMenu.setVisible(true);
                gameHistoryPanel.setVisible(true);
                boardPanel.setVisible(true);
                takenPiecesPanel.setVisible(true);
                gameHistoryPanel.redo(scene.getBoard(), scene.getMoveLog());
                takenPiecesPanel.redo(scene.getMoveLog());
                boardPanel.drawBoard(scene.getBoard());

                try {
                    if (scene.getBoard().getCurrentPlayer().isInCheckMate() || scene.getBoard().getCurrentPlayer().isInStaleMate() || scene.getBoard().getCurrentPlayer().hasSurrendered()) {
                        gameResultDialog.setScene(scene);
                        gameResultDialog.promptUser();
                    }

                    if (scene.getBoard().getCurrentPlayer().isAI()) {
                        boolean execResult = controller.execute("ai_move", true);
                    }
                }
                catch (Exception ignored) {

                }
            }
        }
    }

    @Override
    public void close() {
        gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
    }
}
