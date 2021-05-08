package ru.nsu.spirin.chess.view.swing.board;

import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.player.Alliance;
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
            controller.execute("resign", false);
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
                        if (BoardUtils.isEndGame(scene.getBoard())) {
                            resignButton.setVisible(false);
                            proceedButton.setVisible(true);
                            resultLabel.setVisible(true);
                            if (scene.getBoard().getWhitePlayer().isInStaleMate() || scene.getBoard().getBlackPlayer().isInStaleMate()) {
                                resultLabel.setText("DRAW!");
                            }
                            else if (scene.getPlayerTeam() == Alliance.WHITE) {
                                if (scene.getBoard().getWhitePlayer().isInCheckMate() || scene.getBoard().getWhitePlayer().isResigned()) {
                                    resultLabel.setText("YOU LOST!");
                                }
                                else {
                                    resultLabel.setText("YOU WON!");
                                }
                            }
                            else if (scene.getPlayerTeam() == Alliance.BLACK) {
                                if (scene.getBoard().getBlackPlayer().isInCheckMate() || scene.getBoard().getBlackPlayer().isResigned()) {
                                    resultLabel.setText("YOU LOST!");
                                }
                                else {
                                    resultLabel.setText("YOU WON!");
                                }
                            }

                        }
                        else {
                            resignButton.setVisible(scene.getBoard().getCurrentPlayer().getAlliance() == scene.getPlayerTeam());
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
