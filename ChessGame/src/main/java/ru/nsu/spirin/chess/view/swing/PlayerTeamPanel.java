package ru.nsu.spirin.chess.view.swing;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.GridLayout;

public final class PlayerTeamPanel extends JPanel {
    private final JRadioButton whiteButton;

    public PlayerTeamPanel(String text) {
        super(new GridLayout(1, 1));

        final ButtonGroup group = new ButtonGroup();

        whiteButton = new JRadioButton("WHITE");
        JRadioButton blackButton = new JRadioButton("BLACK");

        group.add(whiteButton);
        group.add(blackButton);

        whiteButton.setSelected(true);

        JPanel playerTeamPanel = new JPanel(new GridLayout(1, 3));

        playerTeamPanel.add(new JLabel(text));
        playerTeamPanel.add(whiteButton);
        playerTeamPanel.add(blackButton);

        add(playerTeamPanel);

        setVisible(true);
    }

    public String getSelectedTeam() {
        return whiteButton.isSelected() ? "white" : "black";
    }
}
