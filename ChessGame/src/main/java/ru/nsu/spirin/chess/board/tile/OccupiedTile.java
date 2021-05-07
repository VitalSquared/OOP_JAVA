package ru.nsu.spirin.chess.board.tile;

import ru.nsu.spirin.chess.pieces.Piece;

public final class OccupiedTile extends Tile {
    private final Piece piece;

    public OccupiedTile(int coordinate, Piece piece) {
        super(coordinate);
        this.piece = piece;
    }

    @Override
    public String toString() {
        return getPiece().getAlliance().isBlack() ?
                getPiece().toString().toLowerCase() :
                getPiece().toString().toUpperCase();
    }

    @Override
    public boolean isTileOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return this.piece;
    }
}
