package ru.nsu.spirin.chessgame.board.tile;

import ru.nsu.spirin.chessgame.pieces.Piece;

public final class OccupiedTile extends Tile {
    private final Piece pieceOnTile;

    public OccupiedTile(int coordinate, Piece pieceOnTile) {
        super(coordinate);
        this.pieceOnTile = pieceOnTile;
    }

    @Override
    public String toString() {
        return getPiece().getPieceAlliance().isBlack() ?
                getPiece().toString().toLowerCase() :
                getPiece().toString().toUpperCase();
    }

    @Override
    public boolean isTileOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return this.pieceOnTile;
    }
}
