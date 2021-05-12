package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

public final class MainMenuPanel extends JPanel {
    public MainMenuPanel(Controller controller) {
        super(new GridLayout(4, 1));

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton aboutButton = new JButton("About");
        JButton exitButton = new JButton("Exit");

        newGameButton.addActionListener(e -> controller.execute("new_game"));
        highScoresButton.addActionListener(e -> controller.execute("high_scores"));
        aboutButton.addActionListener(e -> controller.execute("about"));
        exitButton.addActionListener(e -> controller.execute("exit"));

        add(newGameButton);
        add(highScoresButton);
        add(aboutButton);
        add(exitButton);

        setVisible(true);
    }
}
