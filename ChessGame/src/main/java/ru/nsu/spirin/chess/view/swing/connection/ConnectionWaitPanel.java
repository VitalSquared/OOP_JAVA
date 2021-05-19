package ru.nsu.spirin.chess.view.swing.connection;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.model.match.server.ConnectionStatus;
import ru.nsu.spirin.chess.model.scene.Scene;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;

final class ConnectionWaitPanel extends JPanel {
    private final Scene scene;
    private final JLabel message;

    public ConnectionWaitPanel(Scene scene, Controller controller) {
        super(new BorderLayout());

        this.scene = scene;

        message = new JLabel();
        message.setHorizontalAlignment(SwingConstants.CENTER);
        add(message, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> controller.execute("cancel"));
        add(cancelButton, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void updatePanel() {
        try {
            MatchEntity matchEntity = scene.getActiveGame();
            if (matchEntity.connected() == ConnectionStatus.WAITING_FOR_PLAYER) {
                message.setText("Waiting for other player");
            }
            else if (matchEntity.connected() == ConnectionStatus.NOT_CONNECTED) {
                message.setText("Awaiting connection");
            }
            else {
                message.setText("");
            }
        }
        catch (Exception ignored) {}
    }
}
