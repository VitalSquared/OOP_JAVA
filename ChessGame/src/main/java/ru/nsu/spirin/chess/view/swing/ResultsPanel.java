package ru.nsu.spirin.chess.view.swing;

import com.google.common.collect.Lists;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.properties.ScoresFile;
import ru.nsu.spirin.chess.scene.Scene;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

public final class ResultsPanel extends JPanel {
    private final JLabel scoreLabel;
    private final Scene scene;

    public ResultsPanel(Scene scene, Controller controller) {
        super(new BorderLayout());

        this.scene = scene;

        scoreLabel = new JLabel("");
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            try {
                ScoresFile.saveScore(scene.getActiveGame().getPlayerName(), calculateTotal());
            }
            catch (Exception ignored) {}
            controller.execute("back", false);
        });

        add(scoreLabel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void updatePanel() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Your score:\n\n");
            for (Entry<String, Integer> score : scene.getActiveGame().getScoreTexts()) {
                sb.append(score.getKey()).append(":").append(" ".repeat(20 - score.getKey().length())).append("\t").append(score.getValue()).append("\n");
            }
            sb.append("-".repeat(20)).append("\n");
            sb.append("Total").append(" ".repeat(20 - 5)).append("\t").append(calculateTotal()).append("\n");
            scoreLabel.setText("<html><pre>" + sb + "</pre></html>");
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        catch (Exception ignored) {}
    }

    private int calculateTotal() {
        int total = 0;
        for (Entry<String, Integer> score : scene.getActiveGame().getScoreTexts()) {
            total += score.getValue();
        }
        return total;
    }
}
