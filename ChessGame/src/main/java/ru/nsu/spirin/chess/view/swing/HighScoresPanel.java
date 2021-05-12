package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.view.GameView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;

public final class HighScoresPanel extends JPanel {
    private final JLabel labelScores;
    private final GameView swingView;

    public HighScoresPanel(GameView swingView, Controller controller) {
        super(new BorderLayout());

        this.swingView = swingView;

        labelScores = new JLabel("");
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> controller.execute("back"));

        add(labelScores, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void updatePanel() {
        labelScores.setText("<html><pre>" + swingView.getHighScores() + "</pre></html>");
        labelScores.setFont(new Font("Arial", Font.PLAIN, 20));
        labelScores.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
