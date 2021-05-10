package ru.nsu.spirin.chess.view.swing.connection;

import ru.nsu.spirin.chess.controller.Controller;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;

final class ConnectionWaitPanel extends JPanel {

    public ConnectionWaitPanel(Controller controller) {
        super(new BorderLayout());
        JLabel message = new JLabel("Awaiting connection...");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        add(message, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            controller.execute("cancel", false);
        });
        add(cancelButton, BorderLayout.SOUTH);

        setVisible(false);
    }
}
