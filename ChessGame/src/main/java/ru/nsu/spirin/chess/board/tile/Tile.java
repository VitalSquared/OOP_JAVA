package ru.nsu.spirin.chess.board.tile;

import com.google.common.collect.ImmutableMap;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.pieces.Piece;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile implements Serializable {
    private final int coordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    protected Tile(int coordinate) {
        this.coordinate = coordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public int getCoordinate() {
        return this.coordinate;
    }

    @Override
    public int hashCode() {
        return getPiece() == null ? coordinate : getPiece().hashCode();
    }

    public static Tile createTile(int tileCoordinate, Piece piece) {
        return piece != null ?
                new OccupiedTile(tileCoordinate, piece) :
                EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }
}
