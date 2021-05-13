package ru.nsu.spirin.chess.pieces;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.player.Alliance;

final class PieceUtils {
    private static final Table<Alliance, Integer, Queen>  ALL_POSSIBLE_QUEENS  = PieceUtils.createAllPossibleMovedQueens();
    private static final Table<Alliance, Integer, Rook>   ALL_POSSIBLE_ROOKS   = PieceUtils.createAllPossibleMovedRooks();
    private static final Table<Alliance, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtils.createAllPossibleMovedKnights();
    private static final Table<Alliance, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtils.createAllPossibleMovedBishops();
    private static final Table<Alliance, Integer, Pawn>   ALL_POSSIBLE_PAWNS   = PieceUtils.createAllPossibleMovedPawns();

    static Pawn getMovedPawn(Alliance alliance, int destination) {
        return ALL_POSSIBLE_PAWNS.get(alliance, destination);
    }

    static Knight getMovedKnight(Alliance alliance, int destination) {
        return ALL_POSSIBLE_KNIGHTS.get(alliance, destination);
    }

    static Bishop getMovedBishop(Alliance alliance, int destination) {
        return ALL_POSSIBLE_BISHOPS.get(alliance, destination);
    }

    static Rook getMovedRook(Alliance alliance, int destination) {
        return ALL_POSSIBLE_ROOKS.get(alliance, destination);
    }

    static Queen getMovedQueen(Alliance alliance, int destination) {
        return ALL_POSSIBLE_QUEENS.get(alliance, destination);
    }

    private static Table<Alliance, Integer, Pawn> createAllPossibleMovedPawns() {
        ImmutableTable.Builder<Alliance, Integer, Pawn> pieces = ImmutableTable.builder();
        for (Alliance alliance : Alliance.values()) {
            for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
                pieces.put(alliance, i, new Pawn(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Knight> createAllPossibleMovedKnights() {
        ImmutableTable.Builder<Alliance, Integer, Knight> pieces = ImmutableTable.builder();
        for (Alliance alliance : Alliance.values()) {
            for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
                pieces.put(alliance, i, new Knight(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Bishop> createAllPossibleMovedBishops() {
        ImmutableTable.Builder<Alliance, Integer, Bishop> pieces = ImmutableTable.builder();
        for (Alliance alliance : Alliance.values()) {
            for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
                pieces.put(alliance, i, new Bishop(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Rook> createAllPossibleMovedRooks() {
        ImmutableTable.Builder<Alliance, Integer, Rook> pieces = ImmutableTable.builder();
        for (Alliance alliance : Alliance.values()) {
            for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
                pieces.put(alliance, i, new Rook(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Queen> createAllPossibleMovedQueens() {
        ImmutableTable.Builder<Alliance, Integer, Queen> pieces = ImmutableTable.builder();
        for (Alliance alliance : Alliance.values()) {
            for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
                pieces.put(alliance, i, new Queen(alliance, i, false));
            }
        }
        return pieces.build();
    }
}
