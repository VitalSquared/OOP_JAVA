package ru.nsu.spirin.chess.board;

import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.pieces.Pawn;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.player.Player;

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
        switch (player.getAlliance()) {
            case WHITE -> isWhiteResigned = true;
            case BLACK -> isBlackResigned = true;
        }
    }

    public Board build() {
        return new Board(this);
    }
}
