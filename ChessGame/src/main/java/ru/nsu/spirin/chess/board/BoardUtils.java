package ru.nsu.spirin.chess.board;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public final class BoardUtils {
    public static final String[]             ALGEBRAIC_NOTATION     = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    public static final int TOTAL_NUMBER_OF_TILES  = 64;
    public static final int NUMBER_OF_TILES_IN_ROW = 8;

    private static String[] initializeAlgebraicNotation() {
        String[] notation = new String[TOTAL_NUMBER_OF_TILES];
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
        for (int i = 0; i < TOTAL_NUMBER_OF_TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION[i], i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < TOTAL_NUMBER_OF_TILES;
    }

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.getOrDefault(position, -1);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < TOTAL_NUMBER_OF_TILES ?
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
        } while (column < TOTAL_NUMBER_OF_TILES);
        return false;
    }

    public static boolean isPositionInRow(final int position, int row) {
        row = 8 - row;
        return row * NUMBER_OF_TILES_IN_ROW <= position && position < (row + 1) * NUMBER_OF_TILES_IN_ROW;
    }
}
