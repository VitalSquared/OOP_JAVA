package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;
import ru.nsu.spirin.chess.view.GameView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;

public final class ResultsPanel extends JPanel {
    private final JLabel   scoreLabel;
    private final GameView swingView;
    private final Scene    scene;

    public ResultsPanel(GameView swingView, Scene scene, Controller controller) {
        super(new BorderLayout());

        this.swingView = swingView;
        this.scene = scene;

        scoreLabel = new JLabel("");
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> controller.execute("back"));

        add(scoreLabel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void updatePanel() {
        try {
            scoreLabel.setText("<html><pre>" + swingView.getResultScores() + "</pre></html>");
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        catch (Exception ignored) {
            if (scene.getSceneState() == SceneState.RESULTS_MENU) {
                updatePanel();
            }
        }
    }
}
