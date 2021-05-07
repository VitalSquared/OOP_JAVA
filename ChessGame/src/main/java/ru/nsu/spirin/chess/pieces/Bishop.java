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

public final class Bishop extends Piece {
    private final static int[] MOVE_VECTOR_DIRECTIONS = { -9, -7, 7, 9 };

    public Bishop(Alliance alliance, int coordinate) {
        this(alliance, coordinate, true);
    }

    public Bishop(Alliance alliance, int coordinate, boolean isFirstMove) {
        super(PieceType.BISHOP, alliance, coordinate, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : MOVE_VECTOR_DIRECTIONS) {
            int destinationCoordinate = this.getCoordinate();
            while (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                if (isFirstColumnExclusion(destinationCoordinate, offset) || isEighthColumnExclusion(destinationCoordinate, offset)) {
                    break;
                }
                destinationCoordinate += offset;
                if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                    Tile destinationTile = board.getTile(destinationCoordinate);
                    if (destinationTile.isTileOccupied()) {
                        Piece destinationPiece = destinationTile.getPiece();
                        if (this.getAlliance() != destinationPiece.getAlliance()) {
                            legalMoves.add(new MajorAttackMove(board, this, destinationCoordinate, destinationPiece));
                        }
                        break;
                    }
                    else {
                        legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(Move move) {
        return PieceUtils.getMovedBishop(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }

    private static boolean isFirstColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 1) && (offset == -9 || offset == 7);
    }

    private static boolean isEighthColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 8) && (offset == -7 || offset == 9);
    }
}
