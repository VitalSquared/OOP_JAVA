package ru.nsu.spirin.chess.pieces;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.move.MajorAttackMove;
import ru.nsu.spirin.chess.move.MajorMove;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.board.tile.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Queen extends Piece {
    private final static int[] MOVE_VECTOR_DIRECTIONS = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public Queen(Alliance alliance, int coordinate) {
        this(alliance, coordinate, true);
    }

    public Queen(Alliance alliance, int coordinate, boolean isFirstMove) {
        super(PieceType.QUEEN, alliance, coordinate, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int offset : MOVE_VECTOR_DIRECTIONS) {
            int destinationCoordinate = this.getCoordinate();
            while (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                if (isFirstColumnExclusion(destinationCoordinate, offset) || isEighthColumnExclusion(destinationCoordinate, offset)) {
                    break;
                }
                destinationCoordinate += offset;
                if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                    Tile destinationTile = board.getTile(destinationCoordinate);
                    if (!destinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                    }
                    else {
                        Piece destinationPiece = destinationTile.getPiece();
                        if (this.getAlliance() != destinationPiece.getAlliance()) {
                            legalMoves.add(new MajorAttackMove(board, this, destinationCoordinate, destinationPiece));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Queen movePiece(Move move) {
        return PieceUtils.getMovedQueen(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }

    private static boolean isFirstColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 1) && (offset == -1 || offset == -9 || offset == 7);
    }

    private static boolean isEighthColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 8) && (offset == -7 || offset == 1 || offset == 9);
    }
}
