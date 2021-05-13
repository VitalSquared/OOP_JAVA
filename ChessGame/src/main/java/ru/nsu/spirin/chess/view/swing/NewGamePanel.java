package ru.nsu.spirin.chess.view.swing;

import org.apache.commons.validator.routines.InetAddressValidator;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.utils.SettingsFile;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.GridLayout;

public final class NewGamePanel extends JPanel {
    private final JLabel     playerNamePrompt;
    private final JTextField playerNameInput;
    private final JTextField hostIPInput;
    private final JTextField hostPortInput;
    private final JTextField joinIPInput;
    private final JTextField joinPortInput;
    private final JButton    startButton;
    private final JButton    hostButton;
    private final JButton    joinButton;

    public NewGamePanel(Controller controller) {
        super(new GridLayout(12, 1));

        JPanel playerNamePanel = new JPanel(new GridLayout(1, 2));
        playerNamePrompt = new JLabel("Your Name: ");
        playerNamePrompt.setHorizontalAlignment(SwingConstants.CENTER);
        playerNamePanel.add(playerNamePrompt);
        playerNameInput = new JTextField(SettingsFile.getSettingValue("LAST_USED_NAME"));
        playerNamePanel.add(playerNameInput);
        add(playerNamePanel);

        JLabel playWithAICategoryLabel = new JLabel("--------------------PLAY WITH AI--------------------");
        playWithAICategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(playWithAICategoryLabel);

        PlayerTeamPanel playerTeamPanel = new PlayerTeamPanel("Choose your team: ");
        add(playerTeamPanel);

        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            String team = playerTeamPanel.getSelectedTeam();
            controller.execute("start " + team + " " + playerNameInput.getText());
        });
        add(startButton);

        JLabel hostGameCategoryLabel = new JLabel("--------------------HOST GAME--------------------");
        hostGameCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(hostGameCategoryLabel);

        JPanel hostInfo = new JPanel(new GridLayout(1, 4));
        JLabel hostIPLabel = new JLabel("IP:");
        hostIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hostIPInput = new JTextField("localhost");
        JLabel hostPortLabel = new JLabel("Port:");
        hostPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hostPortInput = new JTextField("5555");
        hostInfo.add(hostIPLabel);
        hostInfo.add(hostIPInput);
        hostInfo.add(hostPortLabel);
        hostInfo.add(hostPortInput);
        add(hostInfo);

        hostButton = new JButton("Host");
        hostButton.addActionListener(e -> controller.execute(
                "host " + hostIPInput.getText() + " " + hostPortInput.getText() + " " + playerNameInput.getText()));
        add(hostButton);

        JLabel joinGameCategoryLabel = new JLabel("--------------------JOIN GAME--------------------");
        joinGameCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(joinGameCategoryLabel);

        JPanel joinInfo = new JPanel(new GridLayout(1, 4));
        JLabel joinIPLabel = new JLabel("IP:");
        joinIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
        joinIPInput = new JTextField("localhost");
        JLabel joinPortLabel = new JLabel("Port:");
        joinPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
        joinPortInput = new JTextField("5555");
        joinInfo.add(joinIPLabel);
        joinInfo.add(joinIPInput);
        joinInfo.add(joinPortLabel);
        joinInfo.add(joinPortInput);
        add(joinInfo);

        joinButton = new JButton("Join");
        joinButton.addActionListener(e -> controller.execute(
                "join " + joinIPInput.getText() + " " + joinPortInput.getText() + " " + playerNameInput.getText()));
        add(joinButton);

        JLabel backCategory = new JLabel("----------------------------------------");
        backCategory.setHorizontalAlignment(SwingConstants.CENTER);
        add(backCategory);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.execute("back"));
        add(backButton);

        setVisible(true);
    }

    public void updatePanel() {
        try {
            startButton.setEnabled(playerNameInput.getText().length() != 0);
            hostButton.setEnabled(playerNameInput.getText().length() != 0 && isIPInputValid(hostIPInput) &&
                                  isPortInputValid(hostPortInput));
            joinButton.setEnabled(playerNameInput.getText().length() != 0 && isIPInputValid(joinIPInput) &&
                                  isPortInputValid(joinPortInput));
            playerNamePrompt.setForeground(playerNameInput.getText().length() != 0 ?
                    Color.BLACK :
                    Color.RED);
        }
        catch (NullPointerException ignored) {
            updatePanel();
        }
    }

    private boolean isIPInputValid(JTextField input) {
        return InetAddressValidator.getInstance().isValidInet4Address(input.getText()) || input.getText().equals("localhost");
    }

    private boolean isPortInputValid(JTextField input) {
        try {
            int port = Integer.parseInt(input.getText());
            return input.getText().length() != 0 && port >= 1 && port <= 65535;
        }
        catch (NumberFormatException ignored) {
        }
        return false;
    }
}
