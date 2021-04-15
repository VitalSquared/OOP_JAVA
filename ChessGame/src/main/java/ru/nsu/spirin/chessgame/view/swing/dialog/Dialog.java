package ru.nsu.spirin.chessgame.view.swing.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

public abstract class Dialog extends JDialog {

    public Dialog(final JFrame frame, final boolean modal) {
        super(frame, modal);
    }

    public abstract void promptUser();
}
