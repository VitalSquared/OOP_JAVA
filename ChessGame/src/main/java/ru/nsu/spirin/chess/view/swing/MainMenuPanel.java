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

        newGameButton.addActionListener(e -> controller.execute("new_game", false));
        highScoresButton.addActionListener(e -> controller.execute("high_scores", false));
        aboutButton.addActionListener(e -> controller.execute("about", false));
        exitButton.addActionListener(e -> controller.execute("exit", false));

        add(newGameButton);
        add(highScoresButton);
        add(aboutButton);
        add(exitButton);

        setVisible(true);
    }
}
