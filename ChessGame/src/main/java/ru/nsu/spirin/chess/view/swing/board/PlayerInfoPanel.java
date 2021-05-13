package ru.nsu.spirin.chess.view.swing.board;

import com.google.common.primitives.Ints;
import ru.nsu.spirin.chess.factory.Factory;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveLog;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.scene.Scene;
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
    private final JLabel  playerNameLabel;
    private final JPanel  playerTakenPieces;
    private       boolean isWhite;

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
        this.playerTakenPieces = new JPanel(new GridLayout(1, 16));
        this.playerTakenPieces.setDoubleBuffered(false);
        this.playerNameLabel = new JLabel("");
        playerNamePanel.setBackground(PANEL_COLOR);
        this.playerTakenPieces.setBackground(PANEL_COLOR);
        playerNamePanel.add(this.playerNameLabel, BorderLayout.WEST);
        add(playerNamePanel, BorderLayout.WEST);
        add(this.playerTakenPieces, BorderLayout.EAST);
        this.isWhite = true;
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void updatePanel(Scene scene, String playerName, MoveLog moveLog) {
        this.playerNameLabel.setText(playerName);
        this.playerNameLabel.setForeground(scene.getActiveGame().getBoard().getCurrentPlayer().getAlliance().isWhite() == isWhite ? Color.RED : Color.BLACK);
        this.playerTakenPieces.removeAll();

        List<Piece> takenPieces = new ArrayList<>();

        for (Pair<Move, String> logEntry : moveLog.getMoves()) {
            Move move = logEntry.getFirst();
            if (move.isAttack()) {
                Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getAlliance().isWhite() && !isWhite) {
                    takenPieces.add(takenPiece);
                }
                else if (takenPiece.getAlliance().isBlack() && isWhite) {
                    takenPieces.add(takenPiece);
                }
            }
        }

        takenPieces.sort((o1, o2) -> Ints.compare(o1.getType().getPieceValue(), o2.getType().getPieceValue()));

        for (Piece takenPiece : takenPieces) {
            BufferedImage image = imageFactory.get(
                    takenPiece.getAlliance().toString().charAt(0) + takenPiece.toString());
            ImageIcon icon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(icon);
            this.playerTakenPieces.add(imageLabel);
        }

        validate();
    }
}
