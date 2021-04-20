package ru.nsu.spirin.chessgame.pieces;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chessgame.player.Alliance;
import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.move.MajorMove;
import ru.nsu.spirin.chessgame.move.pawn.PawnAttackMove;
import ru.nsu.spirin.chessgame.move.pawn.PawnEnPassantAttackMove;
import ru.nsu.spirin.chessgame.move.pawn.PawnJump;
import ru.nsu.spirin.chessgame.move.pawn.PawnMove;
import ru.nsu.spirin.chessgame.move.pawn.PawnPromotion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.PAWN, pieceAlliance, piecePosition, true);
    }

    public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.KING, pieceAlliance, piecePosition, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            int candidateDestinationCoordinate = this.getPiecePosition() + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            //one step up/down
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                if (this.getPieceAlliance().isPawnPromotionTile(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
                }
                else {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
            }
            //two steps up/down
            else if (currentCandidateOffset == 16 && this.isFirstMove() && ((BoardUtils.isPositionInRow(this.getPiecePosition(), 2) && this.getPieceAlliance().isWhite()) || (BoardUtils.isPositionInRow(this.getPiecePosition(), 7) && this.getPieceAlliance().isBlack()))) {
                final int behindCandidateDestinationCoordinate = this.getPiecePosition() + (this.getPieceAlliance().getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            }
            //diagonal move to attack another piece
            else if (currentCandidateOffset == 7 && !((BoardUtils.isPositionInColumn(this.getPiecePosition(), 8) && this.getPieceAlliance().isWhite()) || (BoardUtils.isPositionInColumn(this.getPiecePosition(), 1) && this.getPieceAlliance().isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        if (this.getPieceAlliance().isPawnPromotionTile(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
                else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.getPiecePosition() + (this.getPieceAlliance().getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            }
            //diagonal move to attack another piece
            else if (currentCandidateOffset == 9 && !((BoardUtils.isPositionInColumn(this.getPiecePosition(), 1) && this.getPieceAlliance().isWhite()) || (BoardUtils.isPositionInColumn(this.getPiecePosition(), 8) && this.getPieceAlliance().isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        if (this.getPieceAlliance().isPawnPromotionTile(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
                else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.getPiecePosition() - (this.getPieceAlliance().getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    public Piece getPromotionPiece() {
        return new Queen(this.getPieceAlliance(), this.getPiecePosition(), false);
    }
}
