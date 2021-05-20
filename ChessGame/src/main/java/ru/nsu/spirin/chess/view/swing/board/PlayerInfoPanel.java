package ru.nsu.spirin.chess.view.swing.board;

import com.google.common.primitives.Ints;
import ru.nsu.spirin.chess.factory.Factory;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.move.MoveLog;
import ru.nsu.spirin.chess.model.pieces.Piece;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.utils.Pair;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class PlayerInfoPanel extends JPanel {
    private final JLabel      playerNameLabel;
    private final List<JLabel> playerTakenPieces;
    private       boolean     isWhite;

    private final Factory<BufferedImage> imageFactory;

    private static final EtchedBorder PANEL_BORDER           = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color        PANEL_COLOR            = Color.decode("0xFDFE6");
    private static final Dimension    TAKEN_PIECES_DIMENSION = new Dimension(40, 50);

    public PlayerInfoPanel(Factory<BufferedImage> imageFactory) {
        super(new BorderLayout());

        this.imageFactory = imageFactory;

        setDoubleBuffered(false);
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        JPanel playerNamePanel = new JPanel(new GridLayout(1, 1));

        JPanel playerTakenPieces = new JPanel(new GridLayout(1, 15));
        playerTakenPieces.setDoubleBuffered(false);

        this.playerTakenPieces = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            JLabel label = new JLabel("");
            this.playerTakenPieces.add(0, label);
            playerTakenPieces.add(label);
        }

        this.playerNameLabel = new JLabel("");
        playerNamePanel.setBackground(PANEL_COLOR);
        playerTakenPieces.setBackground(PANEL_COLOR);
        playerNamePanel.add(this.playerNameLabel, BorderLayout.WEST);
        add(playerNamePanel, BorderLayout.WEST);
        add(playerTakenPieces, BorderLayout.EAST);
        this.isWhite = true;
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void updatePanel(Scene scene, String playerName, MoveLog moveLog) {
        this.playerNameLabel.setText(playerName);
        this.playerNameLabel.setForeground(
                scene.getActiveGame().getBoard().getCurrentPlayer().getAlliance().isWhite() == isWhite ?
                        Color.RED :
                        Color.BLACK);

        List<Piece> takenPieces = new ArrayList<>();

        for (Pair<Move, String> logEntry : moveLog.getMoves()) {
            Move move = logEntry.getFirst();
            if (move.isAttack()) {
                Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getAlliance().isWhite() != isWhite) {
                    takenPieces.add(takenPiece);
                }
            }
        }

        takenPieces.sort((o1, o2) -> Ints.compare(o1.getType().getPieceValue(), o2.getType().getPieceValue()));

        int idx = 0;
        for (Piece takenPiece : takenPieces) {
            BufferedImage image = imageFactory.get(takenPiece.getAlliance().toString().charAt(0) + takenPiece.toString());
            this.playerTakenPieces.get(idx++).setIcon(new ImageIcon(image));
        }
        for (int i = idx; i < 15; i++) {
            this.playerTakenPieces.get(i).setIcon(null);
        }

        validate();
    }
}
