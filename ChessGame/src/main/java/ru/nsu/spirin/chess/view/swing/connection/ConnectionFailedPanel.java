package ru.nsu.spirin.chess.view.swing.connection;

import ru.nsu.spirin.chess.controller.Controller;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;

final class ConnectionFailedPanel extends JPanel {

    public ConnectionFailedPanel(Controller controller) {
        super(new GridLayout(2, 1));
        JLabel message = new JLabel("Connection failed...");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        add(message);

        JButton backButton = new JButton("back");
        backButton.addActionListener(e -> controller.execute("back", false));
        add(backButton);

        setVisible(false);
    }
}
