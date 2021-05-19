package ru.nsu.spirin.chess.model.board;

import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.pieces.Pawn;
import ru.nsu.spirin.chess.model.pieces.Piece;
import ru.nsu.spirin.chess.model.player.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class BoardBuilder implements Serializable {
    private final Map<Integer, Piece> boardConfig;
    private       Alliance            nextMoveMaker;
    private       Pawn                enPassantPawn;
    private       boolean             isWhiteResigned;
    private       boolean             isBlackResigned;

    public BoardBuilder() {
        this.boardConfig = new HashMap<>();
        this.nextMoveMaker = Alliance.WHITE;
        this.enPassantPawn = null;
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

    public boolean isWhitePlayerResigned() {
        return this.isWhiteResigned;
    }

    public boolean isBlackPlayerResigned() {
        return this.isBlackResigned;
    }

    public void setPiece(Piece piece) {
        this.boardConfig.put(piece.getCoordinate(), piece);
    }

    public void setMoveMaker(Alliance nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
    }

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public void setPlayerResigned(Player player) {
        switch (player.getAlliance()) {
            case WHITE -> isWhiteResigned = true;
            case BLACK -> isBlackResigned = true;
        }
    }

    public Board build() {
        return new Board(this);
    }
}
