package ru.nsu.spirin.chessgame.board;

import ru.nsu.spirin.chessgame.player.Alliance;
import ru.nsu.spirin.chessgame.pieces.Pawn;
import ru.nsu.spirin.chessgame.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {
    Map<Integer, Piece> boardConfig;
    Alliance nextMoveMaker;
    Pawn enPassantPawn;

    private boolean isWhiteAI;
    private boolean isBlackAI;

    public BoardBuilder() {
        this.boardConfig = new HashMap<>();
        this.isBlackAI = false;
        this.isWhiteAI = false;
    }

    public BoardBuilder setPiece(final Piece piece) {
        this.boardConfig.put(piece.getPiecePosition(), piece);
        return this;
    }

    public BoardBuilder setMoveMaker(final Alliance nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
        return this;
    }

    public Board build() {
        return new Board(this);
    }

    public BoardBuilder setWhiteAI(boolean isWhiteAI) {
        this.isWhiteAI = isWhiteAI;
        return this;
    }

    public BoardBuilder setBlackAI(boolean isBlackAI) {
        this.isBlackAI = isBlackAI;
        return this;
    }

    public boolean isWhiteAI() {
        return this.isWhiteAI;
    }

    public boolean isBlackAI() {
        return this.isBlackAI;
    }

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }
}
