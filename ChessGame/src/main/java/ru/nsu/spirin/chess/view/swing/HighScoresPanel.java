package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public final class HighScoresPanel extends JPanel {
    public HighScoresPanel(Controller controller) {
        super(new GridLayout(2, 1));

        JLabel label = new JLabel("List of high scores:");
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> controller.execute("back", false));

        add(label);
        add(backButton);

        setVisible(false);
    }
}
