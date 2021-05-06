package ru.nsu.spirin.chess.view.swing;

import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.scene.Scene;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridLayout;

public final class NewGamePanel extends JPanel {
    private final JLabel     playerNamePrompt;
    private final JTextField playerName;
    private final JButton    startButton;
    private final JButton    hostButton;
    private final JButton    joinButton;

    public NewGamePanel(Controller controller) {
        super(new GridLayout(10, 1));

        JPanel playerNamePanel = new JPanel(new GridLayout(1, 2));
        playerNamePrompt = new JLabel("Your Name: ");
        playerNamePanel.add(playerNamePrompt);
        playerName = new JTextField("");
        playerNamePanel.add(playerName);
        add(playerNamePanel);

        add(new JLabel("----------PLAY WITH AI---------"));

        PlayerTeamPanel playerTeamPanel = new PlayerTeamPanel("Choose your team: ");
        add(playerTeamPanel);

        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            String team = playerTeamPanel.getSelectedTeam();
            controller.execute("start " + team + " " + playerName.getText(), false);
        });
        add(startButton);

        add(new JLabel("----------HOST A GAME---------"));

        hostButton = new JButton("Host");
        hostButton.addActionListener(e -> controller.execute("host " + playerName.getText(), false));
        add(hostButton);

        add(new JLabel("----------JOIN GAME---------"));

        joinButton = new JButton("Join");
        joinButton.addActionListener(e -> controller.execute("join " + playerName.getText(), false));
        add(joinButton);

        add(new JLabel("---------------------------"));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.execute("back", false));
        add(backButton);

        setVisible(true);
    }

    public void updatePanel(Scene scene) {
        try {
            startButton.setEnabled(playerName.getText().length() != 0);
            hostButton.setEnabled(playerName.getText().length() != 0);
            joinButton.setEnabled(playerName.getText().length() != 0);
            playerNamePrompt.setForeground(playerName.getText().length() != 0 ?
                    Color.BLACK :
                    Color.RED);
        }
        catch (NullPointerException ignored) {}
    }
}
