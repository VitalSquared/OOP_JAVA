package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.view.GameView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public final class AboutPanel extends JPanel {

    public AboutPanel(GameView swingView, Controller controller) {
        super(new BorderLayout());

        String ABOUT_TEXT = "<html><pre>" + swingView.getAbout() + "</pre></html>";
        JLabel label = new JLabel(ABOUT_TEXT);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> controller.execute("back"));

        add(label, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(false);
    }
}
