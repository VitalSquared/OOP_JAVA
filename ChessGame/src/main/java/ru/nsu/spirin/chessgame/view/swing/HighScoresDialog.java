package ru.nsu.spirin.chessgame.view.swing;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class HighScoresDialog extends JDialog {

    public HighScoresDialog(final JFrame frame, final boolean modal) {
        super(frame, modal);
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("List of high scores:"));

        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> HighScoresDialog.this.setVisible(false));

        myPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    public void promptUser() {
        this.setVisible(true);
        repaint();
    }
}
