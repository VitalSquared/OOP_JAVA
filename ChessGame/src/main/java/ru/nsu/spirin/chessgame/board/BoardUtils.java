package ru.nsu.spirin.chessgame.board;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] FIRST_ROW = initRow(56);
    public static final boolean[] SECOND_ROW = initRow(48);
    public static final boolean[] THIRD_ROW = initRow(40);
    public static final boolean[] FOURTH_ROW = initRow(32);
    public static final boolean[] FIFTH_ROW = initRow(24);
    public static final boolean[] SIXTH_ROW = initRow(16);
    public static final boolean[] SEVENTH_ROW = initRow(8);
    public static final boolean[] EIGHTH_ROW = initRow(0);

    public static final String[] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("You can't instantiate BoardUtils class");
    }

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnNumber] = true;
            columnNumber += 8;
        } while (columnNumber < 64);
        return column;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        for (int i = 0; i < NUM_TILES_PER_ROW; i++) {
            row[rowNumber + i] = true;
        }
        return row;
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
        return POSITION_TO_COORDINATE.get(position);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION[coordinate];
    }
}
