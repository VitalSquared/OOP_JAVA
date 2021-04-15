package ru.nsu.spirin.chessgame.view.swing;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class AboutDialog extends JDialog {

    public AboutDialog(final JFrame frame, final boolean modal) {
        super(frame, modal);
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("This is a Chess Game.\n Created by Vitaly Spirin"));

        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> AboutDialog.this.setVisible(false));

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
