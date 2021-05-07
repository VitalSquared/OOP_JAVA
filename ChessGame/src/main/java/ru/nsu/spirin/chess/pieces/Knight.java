package ru.nsu.spirin.chess.pieces;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.move.MajorAttackMove;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MajorMove;
import ru.nsu.spirin.chess.board.tile.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Knight extends Piece {
    private final static int[] MOVE_DIRECTIONS = { -17, -15, -10, -6, 6, 10, 15, 17 };

    public Knight(Alliance alliance, int coordinate) {
        this(alliance, coordinate, true);
    }

    public Knight(Alliance alliance, int coordinate, boolean isFirstMove) {
        super(PieceType.KNIGHT, alliance, coordinate, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int offset : MOVE_DIRECTIONS) {
            int destinationCoordinate = this.getCoordinate() + offset;
            if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                if (isFirstColumnExclusion(this.getCoordinate(), offset) || isSecondColumnExclusion(this.getCoordinate(), offset) ||
                    isSeventhColumnExclusion(this.getCoordinate(), offset) || isEighthColumnExclusion(this.getCoordinate(), offset)) {
                    continue;
                }

                Tile destinationTile = board.getTile(destinationCoordinate);
                if (destinationTile.isTileOccupied()) {
                    Piece destinationPiece = destinationTile.getPiece();
                    if (this.getAlliance() != destinationPiece.getAlliance()) {
                        legalMoves.add(new MajorAttackMove(board, this, destinationCoordinate, destinationPiece));
                    }
                }
                else {
                    legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(Move move) {
        return PieceUtils.getMovedKnight(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }

    private static boolean isFirstColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 1) && ((offset == -17) || (offset == -10) || offset == 6 || offset == 15);
    }

    private static boolean isSecondColumnExclusion(int coordinate, final int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 2) && ((offset == -10) || (offset == 6));
    }

    private static boolean isSeventhColumnExclusion(int coordinate, final int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 7) && ((offset == -6) || (offset == 10));
    }

    private static boolean isEighthColumnExclusion(int coordinate, final int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 8) && ((offset == -15) || (offset == -6) || offset == 10 || offset == 17);
    }
}
