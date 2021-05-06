package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public final class AboutPanel extends JPanel {

    public AboutPanel(Controller controller) {
        super(new GridLayout(2, 1));

        String ABOUT_TEXT = "<html>This is a chess game<br>Created by Vitaly Spirin</html>";
        JLabel label = new JLabel(ABOUT_TEXT);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> controller.execute("back", false));

        add(label);
        add(backButton);

        setVisible(false);
    }
}
