package ru.nsu.spirin.chess.view.swing.board;

import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.view.GameView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public final class GameResultsPanel extends JPanel {
    private final JButton resignButton;
    private final JButton proceedButton;
    private final JLabel  resultLabel;

    private final Scene scene;
    private final GameView swingView;

    public GameResultsPanel(GameView swingView, Scene scene, Controller controller) {
        super(new GridLayout(1, 3));

        this.swingView = swingView;
        this.scene = scene;

        resignButton = new JButton("Resign");
        proceedButton = new JButton("Proceed");
        resultLabel = new JLabel("");

        resignButton.addActionListener(e -> controller.execute("move resign " + scene.getActiveGame().getPlayerAlliance().toString()));

        proceedButton.addActionListener(e -> controller.execute("proceed"));

        resignButton.setVisible(true);
        proceedButton.setVisible(false);
        resultLabel.setVisible(false);

        add(resignButton);
        add(resultLabel);
        add(proceedButton);
    }

    public void updatePanel() {
        if (BoardUtils.isEndGame(scene.getActiveGame().getBoard())) {
            resignButton.setVisible(false);
            proceedButton.setVisible(true);
            resultLabel.setVisible(true);
            resultLabel.setText(swingView.getRoundResult());
        }
        else {
            resignButton.setVisible(true);
            proceedButton.setVisible(false);
            resultLabel.setVisible(false);
        }
    }
}
