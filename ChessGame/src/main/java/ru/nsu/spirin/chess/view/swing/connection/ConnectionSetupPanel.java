package ru.nsu.spirin.chess.view.swing.connection;

import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.scene.Scene;

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
            controller.execute("team none", false);
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (networkEntity.isPlayerReady()) {
                controller.execute("ready", false);
            }
        });
        playerNameWhitePanel.add(whiteToNone, BorderLayout.EAST);
        whitePanel.add(playerNameWhitePanel);

        opponentWhiteReady = new JLabel("");
        opponentWhiteReady.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(opponentWhiteReady);

        playerWhiteReady = new JButton("");
        playerWhiteReady.addActionListener(e -> controller.execute("ready", false));
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
            controller.execute("team white", false);
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (networkEntity.isPlayerReady()) {
                controller.execute("ready", false);
            }
        });
        noneToBlack.addActionListener(e -> {
            controller.execute("team black", false);
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (networkEntity.isPlayerReady()) {
                controller.execute("ready", false);
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
            controller.execute("team none", false);
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (networkEntity.isPlayerReady()) {
                controller.execute("ready", false);
            }
        });
        playerNameBlackPanel.add(blackToNone, BorderLayout.WEST);
        blackPanel.add(playerNameBlackPanel);

        opponentBlackReady = new JLabel("");
        opponentBlackReady.setHorizontalAlignment(SwingConstants.CENTER);
        blackPanel.add(opponentBlackReady);

        playerBlackReady = new JButton("");
        playerBlackReady.addActionListener(e -> controller.execute("ready", false));
        blackPanel.add(playerBlackReady);

        mainPanel.add(blackPanel);



        add(mainPanel, BorderLayout.CENTER);

        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.addActionListener(e -> {
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (networkEntity.isPlayerReady()) {
                controller.execute("ready", false);
            }
            controller.execute("disconnect", false);
        });
        add(disconnectButton, BorderLayout.SOUTH);
        setVisible(false);
    }

    public void updatePanel() {
        try {
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();

            //update white panel
            opponentNameWhite.setVisible(networkEntity.getOpponentTeam() == Alliance.WHITE);
            opponentNameWhite.setText(networkEntity.getOpponentName());

            playerNameWhitePanel.setVisible(networkEntity.getPlayerAlliance() == Alliance.WHITE);
            playerNameWhite.setText(networkEntity.getPlayerName());

            opponentWhiteReady.setVisible(networkEntity.getOpponentTeam() == Alliance.WHITE && networkEntity.getPlayerAlliance() != Alliance.WHITE);
            opponentWhiteReady.setText(networkEntity.isOpponentReady() ? "Ready" : "Not Ready");

            playerWhiteReady.setVisible(networkEntity.getOpponentTeam() != Alliance.WHITE && networkEntity.getPlayerAlliance() == Alliance.WHITE);
            playerWhiteReady.setText(networkEntity.isPlayerReady() ? "Unready" : "Ready");
            //update none panel
            opponentNameNone.setVisible(networkEntity.getOpponentTeam() == null);
            opponentNameNone.setText(networkEntity.getOpponentName());

            playerNameNonePanel.setVisible(networkEntity.getPlayerAlliance() == null);
            playerNameNone.setText(networkEntity.getPlayerName());

            //update black panel
            opponentNameBlack.setVisible(networkEntity.getOpponentTeam() == Alliance.BLACK);
            opponentNameBlack.setText(networkEntity.getOpponentName());

            playerNameBlackPanel.setVisible(networkEntity.getPlayerAlliance() == Alliance.BLACK);
            playerNameBlack.setText(networkEntity.getPlayerName());

            opponentBlackReady.setVisible(networkEntity.getOpponentTeam() == Alliance.BLACK && networkEntity.getPlayerAlliance() != Alliance.BLACK);
            opponentBlackReady.setText(networkEntity.isOpponentReady() ? "Ready" : "Not Ready");

            playerBlackReady.setVisible(networkEntity.getOpponentTeam() != Alliance.BLACK && networkEntity.getPlayerAlliance() == Alliance.BLACK);
            playerBlackReady.setText(networkEntity.isPlayerReady() ? "Unready" : "Ready");
        }
        catch (Exception ignored) {}
    }
}
