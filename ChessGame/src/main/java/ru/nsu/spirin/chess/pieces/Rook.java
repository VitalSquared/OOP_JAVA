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

public final class Rook extends Piece {
    private final static int[] MOVE_VECTOR_DIRECTIONS = { -8, -1, 1, 8 };

    public Rook(Alliance alliance, int coordinate) {
        this(alliance, coordinate, true);
    }

    public Rook(Alliance alliance, int coordinate, boolean isFirstMove) {
        super(PieceType.ROOK, alliance, coordinate, isFirstMove);
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
    public Rook movePiece(Move move) {
        return PieceUtils.getMovedRook(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }

    private static boolean isFirstColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 1) && (offset == -1);
    }

    private static boolean isEighthColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 8) && (offset == 1);
    }
}
