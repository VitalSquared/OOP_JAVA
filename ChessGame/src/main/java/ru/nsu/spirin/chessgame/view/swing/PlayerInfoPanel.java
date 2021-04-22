package ru.nsu.spirin.chessgame.view.swing;

import com.google.common.primitives.Ints;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.move.MoveLog;
import ru.nsu.spirin.chessgame.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class PlayerInfoPanel extends JPanel {
    private final JPanel playerNamePanel;
    private final JLabel playerNameLabel;
    private final JPanel playerTakenPieces;
    private final boolean isWhite;

    private static final EtchedBorder PANEL_BORDER           = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color        PANEL_COLOR            = Color.decode("0xFDFE6");
    private static final Dimension    TAKEN_PIECES_DIMENSION = new Dimension(40, 50);

    public PlayerInfoPanel(final boolean isWhite) {
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.playerNamePanel = new JPanel(new GridLayout(1, 1));
        this.playerTakenPieces = new JPanel(new GridLayout(1, 16));
        this.playerNameLabel = new JLabel("");
        this.playerNamePanel.setBackground(PANEL_COLOR);
        this.playerTakenPieces.setBackground(PANEL_COLOR);
        this.playerNamePanel.add(this.playerNameLabel, BorderLayout.WEST);
        add(this.playerNamePanel, BorderLayout.WEST);
        add(this.playerTakenPieces, BorderLayout.EAST);
        this.isWhite = isWhite;
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final String playerName, final MoveLog moveLog) {
        this.playerNameLabel.setText(playerName);
        this.playerTakenPieces.removeAll();

        final List<Piece> takenPieces = new ArrayList<>();

        for (final Move move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getPieceAlliance().isWhite() && !isWhite) {
                    takenPieces.add(takenPiece);
                }
                else if (takenPiece.getPieceAlliance().isBlack() && isWhite) {
                    takenPieces.add(takenPiece);
                }
            }
        }

        takenPieces.sort((o1, o2) -> Ints.compare(o1.getPieceType().getPieceValue(), o2.getPieceType().getPieceValue()));

        for (final Piece takenPiece : takenPieces) {
            try {
                final BufferedImage image = ImageIO.read(new File("textures/" + takenPiece.getPieceAlliance().toString().charAt(0) + takenPiece.toString() + ".gif"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(icon);
                this.playerTakenPieces.add(imageLabel);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        validate();
    }
}
