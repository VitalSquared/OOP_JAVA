package ru.nsu.spirin.chess.view.swing.connection;

import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.scene.Scene;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

final class ConnectionSetupPanel extends JPanel {
    private final Scene scene;

    private final JLabel opponentNameNone;
    private final JLabel opponentNameWhite;
    private final JLabel opponentNameBlack;

    private final JLabel playerNameNone;
    private final JLabel playerNameWhite;
    private final JLabel playerNameBlack;

    private final JPanel playerNameNonePanel;
    private final JPanel playerNameWhitePanel;
    private final JPanel playerNameBlackPanel;

    private final JLabel opponentWhiteReady;
    private final JLabel opponentBlackReady;

    private final JButton playerWhiteReady;
    private final JButton playerBlackReady;

    public ConnectionSetupPanel(Scene scene, Controller controller) {
        super(new BorderLayout());
        this.scene = scene;

        JPanel mainPanel = new JPanel(new GridLayout(1, 3));



        JPanel whitePanel = new JPanel(new GridLayout(5, 1));
        whitePanel.setBackground(Color.LIGHT_GRAY);

        JLabel whiteHeader = new JLabel("WHITE TEAM");
        whiteHeader.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(whiteHeader);

        opponentNameWhite = new JLabel("");
        opponentNameWhite.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(opponentNameWhite);

        playerNameWhitePanel = new JPanel(new BorderLayout());
        playerNameWhite = new JLabel("");
        playerNameWhite.setVisible(true);
        playerNameWhite.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameWhitePanel.add(playerNameWhite, BorderLayout.CENTER);
        JButton whiteToNone = new JButton(">");
        whiteToNone.addActionListener(e -> {
            controller.execute("team none");
            if (scene.getActiveGame().isPlayerReady()) {
                controller.execute("ready");
            }
        });
        playerNameWhitePanel.add(whiteToNone, BorderLayout.EAST);
        whitePanel.add(playerNameWhitePanel);

        opponentWhiteReady = new JLabel("");
        opponentWhiteReady.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(opponentWhiteReady);

        playerWhiteReady = new JButton("");
        playerWhiteReady.addActionListener(e -> controller.execute("ready"));
        whitePanel.add(playerWhiteReady);

        mainPanel.add(whitePanel);



        JPanel nonePanel = new JPanel(new GridLayout(5, 1));

        nonePanel.add(new JLabel(""));

        opponentNameNone = new JLabel("");
        opponentNameNone.setHorizontalAlignment(SwingConstants.CENTER);
        nonePanel.add(opponentNameNone);

        playerNameNonePanel = new JPanel(new BorderLayout());
        playerNameNone = new JLabel("");
        playerNameNone.setVisible(true);
        playerNameNone.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameNonePanel.add(playerNameNone, BorderLayout.CENTER);
        JButton noneToWhite = new JButton("<");
        JButton noneToBlack = new JButton(">");
        noneToWhite.addActionListener(e -> {
            controller.execute("team white");
            if (scene.getActiveGame().isPlayerReady()) {
                controller.execute("ready");
            }
        });
        noneToBlack.addActionListener(e -> {
            controller.execute("team black");
            if (scene.getActiveGame().isPlayerReady()) {
                controller.execute("ready");
            }
        });
        playerNameNonePanel.add(noneToWhite, BorderLayout.WEST);
        playerNameNonePanel.add(noneToBlack, BorderLayout.EAST);
        nonePanel.add(playerNameNonePanel);

        nonePanel.add(new JLabel(""));

        nonePanel.add(new JLabel(""));

        mainPanel.add(nonePanel);



        JPanel blackPanel = new JPanel(new GridLayout(5, 1));
        blackPanel.setBackground(Color.LIGHT_GRAY);

        JLabel blackHeader = new JLabel("BLACK TEAM");
        blackHeader.setHorizontalAlignment(SwingConstants.CENTER);
        blackPanel.add(blackHeader);

        opponentNameBlack = new JLabel("");
        opponentNameBlack.setHorizontalAlignment(SwingConstants.CENTER);
        blackPanel.add(opponentNameBlack);

        playerNameBlackPanel = new JPanel(new BorderLayout());
        playerNameBlack = new JLabel("");
        playerNameBlack.setVisible(true);
        playerNameBlack.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameBlackPanel.add(playerNameBlack, BorderLayout.CENTER);
        JButton blackToNone = new JButton("<");
        blackToNone.addActionListener(e -> {
            controller.execute("team none");
            if (scene.getActiveGame().isPlayerReady()) {
                controller.execute("ready");
            }
        });
        playerNameBlackPanel.add(blackToNone, BorderLayout.WEST);
        blackPanel.add(playerNameBlackPanel);

        opponentBlackReady = new JLabel("");
        opponentBlackReady.setHorizontalAlignment(SwingConstants.CENTER);
        blackPanel.add(opponentBlackReady);

        playerBlackReady = new JButton("");
        playerBlackReady.addActionListener(e -> controller.execute("ready"));
        blackPanel.add(playerBlackReady);

        mainPanel.add(blackPanel);



        add(mainPanel, BorderLayout.CENTER);

        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.addActionListener(e -> {
            if (scene.getActiveGame().isPlayerReady()) {
                controller.execute("ready");
            }
            controller.execute("disconnect");
        });
        add(disconnectButton, BorderLayout.SOUTH);
        setVisible(false);
    }

    public void updatePanel() {
        try {
            MatchEntity matchEntity = scene.getActiveGame();

            //update white panel
            opponentNameWhite.setVisible(matchEntity.getOpponentAlliance() == Alliance.WHITE);
            opponentNameWhite.setText(matchEntity.getOpponentName());

            playerNameWhitePanel.setVisible(matchEntity.getPlayerAlliance() == Alliance.WHITE);
            playerNameWhite.setText(matchEntity.getPlayerName());

            opponentWhiteReady.setVisible(matchEntity.getOpponentAlliance() == Alliance.WHITE && matchEntity.getPlayerAlliance() != Alliance.WHITE);
            opponentWhiteReady.setText(matchEntity.isOpponentReady() ? "Ready" : "Not Ready");

            playerWhiteReady.setVisible(matchEntity.getOpponentAlliance() != Alliance.WHITE && matchEntity.getPlayerAlliance() == Alliance.WHITE);
            playerWhiteReady.setText(matchEntity.isPlayerReady() ? "Unready" : "Ready");
            //update none panel
            opponentNameNone.setVisible(matchEntity.getOpponentAlliance() == null);
            opponentNameNone.setText(matchEntity.getOpponentName());

            playerNameNonePanel.setVisible(matchEntity.getPlayerAlliance() == null);
            playerNameNone.setText(matchEntity.getPlayerName());

            //update black panel
            opponentNameBlack.setVisible(matchEntity.getOpponentAlliance() == Alliance.BLACK);
            opponentNameBlack.setText(matchEntity.getOpponentName());

            playerNameBlackPanel.setVisible(matchEntity.getPlayerAlliance() == Alliance.BLACK);
            playerNameBlack.setText(matchEntity.getPlayerName());

            opponentBlackReady.setVisible(matchEntity.getOpponentAlliance() == Alliance.BLACK && matchEntity.getPlayerAlliance() != Alliance.BLACK);
            opponentBlackReady.setText(matchEntity.isOpponentReady() ? "Ready" : "Not Ready");

            playerBlackReady.setVisible(matchEntity.getOpponentAlliance() != Alliance.BLACK && matchEntity.getPlayerAlliance() == Alliance.BLACK);
            playerBlackReady.setText(matchEntity.isPlayerReady() ? "Unready" : "Ready");
        }
        catch (Exception ignored) {}
    }
}
