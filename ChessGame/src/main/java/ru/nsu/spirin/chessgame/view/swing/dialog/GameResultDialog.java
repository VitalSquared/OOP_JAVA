package ru.nsu.spirin.chessgame.view.swing.dialog;

import ru.nsu.spirin.chessgame.scene.Scene;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class GameResultDialog extends Dialog {
    private       Scene  scene = null;
    private final JLabel label;

    public GameResultDialog(JFrame frame, boolean modal) {
        super(frame, modal);
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));

        getContentPane().add(myPanel);
        label = new JLabel("");
        myPanel.add(label);

        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            GameResultDialog.this.setVisible(false);
            if (scene != null) {
                scene.setBoard(null);
            }
        });

        myPanel.add(okButton);

        myPanel.invalidate();

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    @Override
    public void promptUser() {
        if (scene.getBoard() != null) {
            label.setText("[" + scene.getBoard().getCurrentPlayer().getOpponent().getAlliance() + "] " + scene.getBoard().getCurrentPlayer().getOpponent().getPlayerName() + " won!");
        }
        this.setVisible(true);
        repaint();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
