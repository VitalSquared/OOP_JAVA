package ru.nsu.spirin.chess.model.pieces;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.move.MajorAttackMove;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.move.MajorMove;
import ru.nsu.spirin.chess.model.board.tile.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class King extends Piece {
    private final static int[] MOVE_DIRECTIONS = {-9, -8, -7, -1, 1, 7, 8, 9};

    private final boolean isCastled;
    private final boolean kingSideCastleCapable;
    private final boolean queenSideCastleCapable;

    public King(Alliance alliance, int coordinate, boolean kingSideCastleCapable, boolean queenSideCastleCapable) {
        this(alliance, coordinate, true, false, kingSideCastleCapable, queenSideCastleCapable);
    }

    public King(Alliance alliance, int coordinate, boolean isFirstMove, boolean isCastled, boolean kingSideCastleCapable, boolean queenSideCastleCapable) {
        super(PieceType.KING, alliance, coordinate, isFirstMove);
        this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    public boolean isKingSideCastleCapable() {
        return this.kingSideCastleCapable;
    }

    public boolean isQueenSideCastleCapable() {
        return this.queenSideCastleCapable;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int offset : MOVE_DIRECTIONS) {
            int destinationCoordinate = this.getCoordinate() + offset;
            if (isFirstColumnExclusion(this.getCoordinate(), offset) || isEighthColumnExclusion(this.getCoordinate(), offset)) {
                continue;
            }
            if (BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
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
    public King movePiece(Move move) {
        return new King(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false, move.isCastlingMove(), false, false);
    }

    private static boolean isFirstColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 1) && (offset == -9 || offset == -1 || offset == 7);
    }

    private static boolean isEighthColumnExclusion(int coordinate, int offset) {
        return BoardUtils.isPositionInColumn(coordinate, 8) && ((offset == -7) || (offset == 1 || offset == 9));
    }
}
