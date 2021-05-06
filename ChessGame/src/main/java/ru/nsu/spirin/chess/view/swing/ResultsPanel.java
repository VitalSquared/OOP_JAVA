package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public final class ResultsPanel extends JPanel {
    public ResultsPanel(Controller controller) {
        super(new GridLayout(2, 1));

        String ABOUT_TEXT = "Game Over!";
        JLabel label = new JLabel(ABOUT_TEXT);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> controller.execute("back", false));

        add(label);
        add(backButton);

        setVisible(false);
    }

    public void updatePanel(Scene scene) {

    }
}
