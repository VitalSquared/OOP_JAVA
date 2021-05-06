package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public final class ConnectionPanel extends JPanel {
    private final JLabel playerName = new JLabel("");
    private final JLabel opponentName = new JLabel("");
    private final JPanel playerRow = new JPanel(new GridBagLayout());
    private final JPanel opponentRow = new JPanel(new GridBagLayout());

    public ConnectionPanel(Controller controller) {
        super(new GridLayout(4, 1));
        add(new JLabel("CONNECTION MENU"));

        PlayerTeamPanel playerTeamPanel = new PlayerTeamPanel("Choose your team: ");
        playerRow.add(playerName);
        playerRow.add(playerTeamPanel);

        PlayerTeamPanel opponentTeamPanel = new PlayerTeamPanel("Opponent's team: ");
        opponentRow.add(opponentName);
        opponentRow.add(opponentTeamPanel);

        add(playerRow);
        add(opponentRow);

        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.addActionListener(e -> controller.execute("disconnect", false));
        add(disconnectButton);

        setVisible(false);
    }

    public void updatePanel(Scene scene) {
        /*if (this.isVisible() && session.getActiveGame() != null) {
            playerName.setText(session.getActiveGame().getPlayerName());
            //opponentName.setText(session.getActiveGame().getOpponentName());
        }
        else {
            playerName.setText("");
            opponentName.setText("");
        }*/
    }
}
