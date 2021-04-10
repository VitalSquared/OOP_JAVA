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

    public BoardBuilder() {
        this.boardConfig = new HashMap<>();
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

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }
}
