package ru.nsu.spirin.chess.board.tile;

import ru.nsu.spirin.chess.pieces.Piece;

public final class OccupiedTile extends Tile {
    private final Piece pieceOnTile;

    public OccupiedTile(final int coordinate, final Piece pieceOnTile) {
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
