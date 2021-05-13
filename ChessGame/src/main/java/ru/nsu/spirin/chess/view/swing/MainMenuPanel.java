package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.factory.Factory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

public final class MainMenuPanel extends JPanel {
    public MainMenuPanel(Controller controller, Factory<BufferedImage> imageFactory) {
        super(new GridLayout(2, 1));

        JPanel panel = new JPanel(new GridLayout(4, 1));

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton aboutButton = new JButton("About");
        JButton exitButton = new JButton("Exit");

        newGameButton.addActionListener(e -> controller.execute("new_game"));
        highScoresButton.addActionListener(e -> controller.execute("high_scores"));
        aboutButton.addActionListener(e -> controller.execute("about"));
        exitButton.addActionListener(e -> controller.execute("exit"));

        panel.add(newGameButton);
        panel.add(highScoresButton);
        panel.add(aboutButton);
        panel.add(exitButton);

        add(new JLabel(new ImageIcon(imageFactory.get("GAME_LOGO"))));
        add(panel);

        setVisible(true);
    }
}
