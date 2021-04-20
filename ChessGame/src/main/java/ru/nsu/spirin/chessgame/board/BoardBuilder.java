package ru.nsu.spirin.chessgame.board;

import ru.nsu.spirin.chessgame.player.Alliance;
import ru.nsu.spirin.chessgame.pieces.Pawn;
import ru.nsu.spirin.chessgame.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public final class BoardBuilder {
    private final Map<Integer, Piece> boardConfig;
    private       Alliance            nextMoveMaker;
    private       Pawn                enPassantPawn;
    private       boolean             isWhiteAI;
    private       boolean             isBlackAI;

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

    public void setPiece(final Piece piece) {
        this.boardConfig.put(piece.getPiecePosition(), piece);
    }

    public void setMoveMaker(final Alliance nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
    }

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public void setWhiteAI(boolean isWhiteAI) {
        this.isWhiteAI = isWhiteAI;
    }

    public void setBlackAI(boolean isBlackAI) {
        this.isBlackAI = isBlackAI;
    }

    public Board build() {
        return new Board(this);
    }
}
