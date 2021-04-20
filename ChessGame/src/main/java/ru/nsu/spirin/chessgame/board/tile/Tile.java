package ru.nsu.spirin.chessgame.board.tile;

import com.google.common.collect.ImmutableMap;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    private final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    protected Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ?
                new OccupiedTile(tileCoordinate, piece) :
                EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }
}
