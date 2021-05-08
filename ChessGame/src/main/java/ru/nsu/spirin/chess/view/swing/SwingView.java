package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;
import ru.nsu.spirin.chess.view.GameView;
import ru.nsu.spirin.chess.view.swing.board.BoardPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;

public final class SwingView extends GameView {

    private final JFrame           gameFrame;

    private final MainMenuPanel   mainMenuPanel;
    private final NewGamePanel    newGamePanel;
    private final HighScoresPanel highScoresPanel;
    private final AboutPanel      aboutPanel;
    private final ConnectionPanel connectionPanel;
    private final BoardPanel      boardPanel;
    private final ResultsPanel    resultsPanel;

    public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(620, 640);
    public static final Dimension TILE_PANEL_DIMENSION  = new Dimension(10, 10);
    public static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 400);

    public SwingView(Scene scene, Controller controller) {
        super(scene, controller);

        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };
        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);

        this.mainMenuPanel = new MainMenuPanel(controller);
        this.newGamePanel = new NewGamePanel(controller);
        this.highScoresPanel = new HighScoresPanel(controller);
        this.aboutPanel = new AboutPanel(controller);
        this.connectionPanel = new ConnectionPanel(controller);
        this.boardPanel = new BoardPanel(scene, controller);
        this.resultsPanel = new ResultsPanel(scene, controller);

        panel.add(this.mainMenuPanel);
        panel.add(this.newGamePanel);
        panel.add(this.highScoresPanel);
        panel.add(this.aboutPanel);
        panel.add(this.connectionPanel);
        panel.add(this.boardPanel);
        panel.add(this.resultsPanel);

        this.gameFrame.add(panel);
        this.gameFrame.setVisible(true);
    }

    @Override
    public void render() {
        updateMainMenuPanel(getScene());
        updateNewGamePanel(getScene());
        updateHighScoresPanel(getScene());
        updateAboutPanel(getScene());
        updateConnectionPanel(getScene());
        updateBoardPanel(getScene());
        updateResultsPanel(getScene());
    }

    @Override
    public void close() {
        gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void updateMainMenuPanel(Scene scene) {
        mainMenuPanel.setVisible(scene.getSceneState() == SceneState.MAIN_MENU);
    }

    private void updateNewGamePanel(Scene scene) {
        newGamePanel.setVisible(scene.getSceneState() == SceneState.NEW_GAME_MENU);
        if (scene.getSceneState() == SceneState.NEW_GAME_MENU) {
            newGamePanel.updatePanel(scene);
        }
    }

    private void updateHighScoresPanel(Scene scene) {
        highScoresPanel.setVisible(scene.getSceneState() == SceneState.HIGH_SCORES_MENU);
        if (scene.getSceneState() == SceneState.HIGH_SCORES_MENU) {
            highScoresPanel.updatePanel();
        }
    }

    private void updateAboutPanel(Scene scene) {
        aboutPanel.setVisible(scene.getSceneState() == SceneState.ABOUT_MENU);
    }

    private void updateConnectionPanel(Scene scene) {
        connectionPanel.setVisible(scene.getSceneState() == SceneState.CONNECTION_MENU);
        if (scene.getSceneState() == SceneState.CONNECTION_MENU) {
            connectionPanel.updatePanel(scene);
        }
    }

    private void updateBoardPanel(Scene scene) {
        boardPanel.setVisible(scene.getSceneState() == SceneState.BOARD_MENU);
        if (scene.getSceneState() == SceneState.BOARD_MENU) {
            boardPanel.updatePanel();
        }
    }

    private void updateResultsPanel(Scene scene) {
        resultsPanel.setVisible(scene.getSceneState() == SceneState.RESULTS_MENU);
        if (scene.getSceneState() == SceneState.RESULTS_MENU) {
            resultsPanel.updatePanel();
        }
    }
}
