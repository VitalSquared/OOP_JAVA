package ru.nsu.spirin.chess.view.swing.connection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;

final class ConnectionWaitPanel extends JPanel {

    public ConnectionWaitPanel() {
        super(new BorderLayout());
        JLabel message = new JLabel("Awaiting connection...");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        add(message);
        setVisible(false);
    }
}
