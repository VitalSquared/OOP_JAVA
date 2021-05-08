package ru.nsu.spirin.chess.view.swing;

import com.google.common.collect.Lists;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.properties.ScoresFile;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

public final class HighScoresPanel extends JPanel {
    private final JLabel labelScores;

    public HighScoresPanel(Controller controller) {
        super(new BorderLayout());

        labelScores = new JLabel("");
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> controller.execute("back", false));

        add(labelScores, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void updatePanel() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of top 10 high scores:\n\n");
        for (Entry<String, Integer> score : calculateScores()) {
            sb.append(score.getKey()).append(" ".repeat(20 - score.getKey().length())).append("\t").append(score.getValue()).append("\n");
        }
        labelScores.setText("<html><pre>" + sb + "</pre></html>");
        labelScores.setFont(new Font("Arial", Font.PLAIN, 20));
        labelScores.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private List<Entry<String, Integer>> calculateScores() {
        Properties scores = ScoresFile.getScores();
        List<Entry<String, Integer>> scoresList = new ArrayList<>();
        for (String score : scores.stringPropertyNames()) {
            scoresList.add(new AbstractMap.SimpleEntry<>(score, Integer.parseInt((String) scores.get(score))));
        }
        scoresList.sort(Entry.comparingByValue());
        return Lists.reverse(scoresList).stream().skip(Math.max(0, scoresList.size() - 10)).collect(Collectors.toList());
    }
}
