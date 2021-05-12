package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.factory.Factory;
import ru.nsu.spirin.chess.factory.ImageFactory;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;
import ru.nsu.spirin.chess.view.GameView;
import ru.nsu.spirin.chess.view.swing.board.BoardPanel;
import ru.nsu.spirin.chess.view.swing.connection.ConnectionPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    public SwingView(Scene scene, Controller controller) throws IOException {
        super(scene, controller);

        Factory<BufferedImage> imageFactory = new ImageFactory();

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
        this.highScoresPanel = new HighScoresPanel(this, controller);
        this.aboutPanel = new AboutPanel(this, controller);
        this.connectionPanel = new ConnectionPanel(scene, controller);
        this.boardPanel = new BoardPanel(this, scene, controller, imageFactory);
        this.resultsPanel = new ResultsPanel(this, controller);

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
        if (viewChanged()) {
            updateMainMenuPanel(getScene());
            updateNewGamePanel(getScene());
            updateHighScoresPanel(getScene());
            updateAboutPanel(getScene());
            updateConnectionPanel(getScene());
            updateBoardPanel(getScene());
            updateResultsPanel(getScene());
        }
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
            connectionPanel.updatePanel();
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

    @Override
    protected boolean viewChanged() {
        boolean changed = super.viewChanged();
        if (changed) return true;

        if (mainMenuPanel.isVisible() != (getScene().getSceneState() == SceneState.MAIN_MENU)) return true;
        if (newGamePanel.isVisible() != (getScene().getSceneState() == SceneState.NEW_GAME_MENU)) return true;
        if (highScoresPanel.isVisible() != (getScene().getSceneState() == SceneState.HIGH_SCORES_MENU)) return true;
        if (aboutPanel.isVisible() != (getScene().getSceneState() == SceneState.ABOUT_MENU)) return true;
        if (connectionPanel.isVisible() != (getScene().getSceneState() == SceneState.CONNECTION_MENU)) return true;
        if (boardPanel.isVisible() != (getScene().getSceneState() == SceneState.BOARD_MENU)) return true;

        return resultsPanel.isVisible() != (getScene().getSceneState() == SceneState.RESULTS_MENU);
    }
}
