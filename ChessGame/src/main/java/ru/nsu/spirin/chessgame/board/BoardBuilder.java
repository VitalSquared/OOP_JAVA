package ru.nsu.spirin.chessgame.board;

import ru.nsu.spirin.chessgame.player.Alliance;
import ru.nsu.spirin.chessgame.pieces.Pawn;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.player.Player;

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
        this.boardConfig.put(piece.getPiecePosition(), piece);
    }

    public void setMoveMaker(final Alliance nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
    }

    public void setEnPassantPawn(final Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public void setWhiteAI(final boolean isWhiteAI) {
        this.isWhiteAI = isWhiteAI;
    }

    public void setBlackAI(final boolean isBlackAI) {
        this.isBlackAI = isBlackAI;
    }

    public void setWhitePlayerName(final String whitePlayerName) {
        this.whitePlayerName = whitePlayerName;
    }

    public void setBlackPlayerName(final String blackPlayerName) {
        this.blackPlayerName = blackPlayerName;
    }

    public void copyPlayerInfo(final Player whitePlayer, final Player blackPlayer) {
        setWhiteAI(whitePlayer.isAI());
        setWhitePlayerName(whitePlayer.getPlayerName());
        setBlackAI(blackPlayer.isAI());
        setBlackPlayerName(blackPlayer.getPlayerName());
    }

    public Board build() {
        return new Board(this);
    }
}
