package ru.nsu.spirin.chess.pieces;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.PawnAttackMove;
import ru.nsu.spirin.chess.move.PawnEnPassantAttackMove;
import ru.nsu.spirin.chess.move.PawnJump;
import ru.nsu.spirin.chess.move.PawnMove;
import ru.nsu.spirin.chess.move.PawnPromotion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Pawn extends Piece {
    private final static int[] MOVE_DIRECTIONS = { 8, 16, 7, 9 };

    public Pawn(Alliance alliance, int coordinate) {
        this(alliance, coordinate, true);
    }

    public Pawn(Alliance alliance, int coordinate, boolean isFirstMove) {
        super(PieceType.PAWN, alliance, coordinate, isFirstMove);
    }

    public Piece getPromotionPiece() {
        return new Queen(this.getAlliance(), this.getCoordinate(), false);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int offset : MOVE_DIRECTIONS) {
            int destinationCoordinate = this.getCoordinate() + (this.getAlliance().getDirection() * offset);
            if (!BoardUtils.isValidTileCoordinate(destinationCoordinate)) {
                continue;
            }
            //one step up/down
            if (offset == 8 && !board.getTile(destinationCoordinate).isTileOccupied()) {
                if (this.getAlliance().isPawnPromotionTile(destinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, destinationCoordinate)));
                }
                else {
                    legalMoves.add(new PawnMove(board, this, destinationCoordinate));
                }
            }
            //two steps up/down
            else if (offset == 16 && this.isFirstMove() &&
                     ((BoardUtils.isPositionInRow(this.getCoordinate(), 2) && this.getAlliance().isWhite()) || (BoardUtils.isPositionInRow(this.getCoordinate(), 7) && this.getAlliance().isBlack()))) {
                int beforeDestinationCoordinate = this.getCoordinate() + (this.getAlliance().getDirection() * 8);
                if (!board.getTile(beforeDestinationCoordinate).isTileOccupied() && !board.getTile(destinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, destinationCoordinate));
                }
            }
            //diagonal move to attack another piece
            else if ((offset == 7 || offset == 9) && !isColumnExclusion(this.getCoordinate(), this.getAlliance(), offset == 7 ? 8 : 1, offset == 7 ? 1 : 8)) {
                int sign = offset == 7 ? 1 : -1;
                if (board.getTile(destinationCoordinate).isTileOccupied()) {
                    Piece destinationPiece = board.getTile(destinationCoordinate).getPiece();
                    if (this.getAlliance() != destinationPiece.getAlliance()) {
                        if (this.getAlliance().isPawnPromotionTile(destinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, destinationCoordinate, destinationPiece)));
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, this, destinationCoordinate, destinationPiece));
                        }
                    }
                }
                else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getCoordinate() == (this.getCoordinate() + sign * (this.getAlliance().getOppositeDirection()))) {
                        Piece destinationPiece = board.getEnPassantPawn();
                        if (this.getAlliance() != destinationPiece.getAlliance()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, destinationCoordinate, destinationPiece));
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return PieceUtils.getMovedPawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate());
    }

    private static boolean isColumnExclusion(int coordinate, Alliance alliance, int whiteColumn, int blackColumn) {
        return ((BoardUtils.isPositionInColumn(coordinate, whiteColumn) && alliance.isWhite()) ||
                (BoardUtils.isPositionInColumn(coordinate, blackColumn) && alliance.isBlack()));
    }
}
