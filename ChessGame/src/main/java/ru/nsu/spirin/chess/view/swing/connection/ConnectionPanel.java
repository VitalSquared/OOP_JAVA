package ru.nsu.spirin.chess.view.swing.connection;

import ru.nsu.spirin.chess.communication.ConnectionStatus;
import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import java.awt.BorderLayout;
import java.awt.LayoutManager;

public final class ConnectionPanel extends JPanel {
    private final Scene scene;

    private final ConnectionWaitPanel connectionWaitPanel;
    private final ConnectionFailedPanel connectionFailedPanel;
    private final ConnectionSetupPanel connectionSetupPanel;

    public ConnectionPanel(Scene scene, Controller controller) {
        super(new BorderLayout());
        this.scene = scene;

        JPanel panel = new JPanel() {
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };
        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);

        this.connectionWaitPanel = new ConnectionWaitPanel(controller);
        this.connectionFailedPanel = new ConnectionFailedPanel(controller);
        this.connectionSetupPanel = new ConnectionSetupPanel(scene, controller);

        panel.add(this.connectionWaitPanel);
        panel.add(this.connectionFailedPanel);
        panel.add(this.connectionSetupPanel);

        add(panel, BorderLayout.CENTER);

        setVisible(false);
    }

    public void updatePanel() {
        NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
        try {
            ConnectionStatus status = networkEntity.connected();
            this.connectionWaitPanel.setVisible(status == ConnectionStatus.NOT_CONNECTED);
            this.connectionSetupPanel.setVisible(status == ConnectionStatus.CONNECTED);
            this.connectionFailedPanel.setVisible(status == ConnectionStatus.FAILED);

            if (status == ConnectionStatus.CONNECTED) {
                this.connectionSetupPanel.updatePanel();
            }
        }
        catch (Exception ignored) {
            updatePanel();
        }
    }
}
