package ru.nsu.spirin.chess.view.swing.board;

import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.player.Player;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public final class GameResultsPanel extends JPanel {
    private final JButton resignButton;
    private final JButton proceedButton;
    private final JLabel  resultLabel;

    public GameResultsPanel(Scene scene, Controller controller) {
        super(new GridLayout(1, 3));
        resignButton = new JButton("Resign");
        proceedButton = new JButton("Proceed");
        resultLabel = new JLabel("");

        resignButton.addActionListener(e -> {
            controller.execute("move resign " + scene.getActiveGame().getPlayerAlliance().toString(), false);
        });

        proceedButton.addActionListener(e -> {
            controller.execute("proceed", false);
        });

        resignButton.setVisible(true);
        proceedButton.setVisible(false);
        resultLabel.setVisible(false);

        add(resignButton);
        add(resultLabel);
        add(proceedButton);

        new Thread(() -> {
            while (scene.getSceneState() != SceneState.NONE) {
                try {
                    if (scene.getSceneState() == SceneState.BOARD_MENU) {
                        if (BoardUtils.isEndGame(scene.getActiveGame().getBoard())) {
                            resignButton.setVisible(false);
                            proceedButton.setVisible(true);
                            resultLabel.setVisible(true);
                            if (scene.getActiveGame().getBoard().getCurrentPlayer().isInStaleMate()) {
                                resultLabel.setText("DRAW!");
                            }
                            else {
                                Player alliancePlayer = scene.getActiveGame().getPlayerAlliance().choosePlayer(scene.getActiveGame().getBoard().getWhitePlayer(), scene.getActiveGame().getBoard().getBlackPlayer());
                                if (scene.getActiveGame().getPlayerAlliance() == Alliance.WHITE) {
                                    if (alliancePlayer.isInCheckMate() || alliancePlayer.isResigned()) {
                                        resultLabel.setText("YOU LOST!");
                                    }
                                    else {
                                        resultLabel.setText("YOU WON!");
                                    }
                                }
                            }
                        }
                        else {
                            resignButton.setVisible(true);
                            proceedButton.setVisible(false);
                            resultLabel.setVisible(false);
                        }
                    }
                }
                catch (Exception ignored) {
                }
            }
        }).start();
    }
}
