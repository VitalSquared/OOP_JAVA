package ru.nsu.spirin.chess.board;

import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.pieces.Pawn;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.player.Player;

import java.util.HashMap;
import java.util.Map;

public final class BoardBuilder {
    private final Map<Integer, Piece> boardConfig;
    private       Alliance            nextMoveMaker;
    private       Pawn                enPassantPawn;
    private       boolean             isWhiteAI;
    private       boolean             isBlackAI;
    private       String              whitePlayerName;
    private       String              blackPlayerName;

    public BoardBuilder() {
        this.boardConfig = new HashMap<>();
        this.nextMoveMaker = Alliance.WHITE;
        this.enPassantPawn = null;
        this.isBlackAI = false;
        this.isWhiteAI = false;
    }

    public Map<Integer, Piece> getBoardConfig() {
        return this.boardConfig;
    }

    public Alliance getNextMoveMaker() {
        return this.nextMoveMaker;
    }

    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    public boolean isWhiteAI() {
        return this.isWhiteAI;
    }

    public boolean isBlackAI() {
        return this.isBlackAI;
    }

    public String getWhitePlayerName() {
        return this.whitePlayerName;
    }

    public String getBlackPlayerName() {
        return this.blackPlayerName;
    }

    public void setPiece(final Piece piece) {
        this.boardConfig.put(piece.getCoordinate(), piece);
    }

    public void setMoveMaker(final Alliance nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
    }

    public void setEnPassantPawn(final Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public void setPlayerResigned(Player player) {
        player.resign();
    }

    public Board build() {
        return new Board(this);
    }
}
