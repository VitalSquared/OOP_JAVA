package ru.nsu.spirin.chessgame.board;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public final class BoardUtils {
    public static final String[]             ALGEBRAIC_NOTATION     = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    public static final int NUM_TILES         = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() throws IllegalCallerException {
        throw new IllegalCallerException("You can't instantiate BoardUtils class");
    }

    private static String[] initializeAlgebraicNotation() {
        String[] notation = new String[NUM_TILES];
        int idx = 0;
        for (int i = 8; i >= 1; i--) {
            for (char j = 'a'; j <= 'h'; j++) {
                notation[idx++] = j + "" + i;
            }
        }
        return notation;
    }

    private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION[i], i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.getOrDefault(position, -1);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES ?
                ALGEBRAIC_NOTATION[coordinate] :
                " ";
    }

    public static boolean isPositionInColumn(final int position, int column) {
        column--;
        do {
            if (position == column) {
                return true;
            }
            column += 8;
        } while (column < NUM_TILES);
        return false;
    }

    public static boolean isPositionInRow(final int position, int row) {
        row = 8 - row;
        return row * NUM_TILES_PER_ROW <= position && position < (row + 1) * NUM_TILES_PER_ROW;
    }
}
