package ru.nsu.spirin.chess.view.swing.connection;

import ru.nsu.spirin.chess.controller.Controller;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;

final class ConnectionFailedPanel extends JPanel {

    public ConnectionFailedPanel(Controller controller) {
        super(new BorderLayout());
        JLabel message = new JLabel("Connection failed...");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        add(message, BorderLayout.CENTER);

        JButton backButton = new JButton("back");
        backButton.addActionListener(e -> controller.execute("back"));
        add(backButton, BorderLayout.SOUTH);

        setVisible(false);
    }
}
