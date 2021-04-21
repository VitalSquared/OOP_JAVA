package ru.nsu.spirin.chessgame.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.move.MoveStatus;
import ru.nsu.spirin.chessgame.move.MoveTransition;
import ru.nsu.spirin.chessgame.move.pawn.PawnPromotion;
import ru.nsu.spirin.chessgame.pieces.King;
import ru.nsu.spirin.chessgame.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    private final Board            board;
    private final King             playerKing;
    private final Collection<Move> legalMoves;
    private final boolean          isAI;
    private final boolean          isInCheck;
    private       boolean          hasSurrendered;
    private       int              promotedPawns;
    private final String           playerName;

    protected Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves, final boolean isAI, final String playerName) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
        this.isAI = isAI;
        this.promotedPawns = 0;
        this.playerName = playerName;
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);

    public Board getBoard() {
        return this.board;
    }

    public Piece getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public boolean isAI() {
        return this.isAI;
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Board doesn't have a king!");
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {
        for (final Move move : legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    public boolean hasSurrendered() {
        return this.hasSurrendered;
    }

    public int getPromotedPawns() {
        return this.promotedPawns;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.getCurrentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        if (move instanceof PawnPromotion) {
            this.promotedPawns++;
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : moves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    public void surrender() {
        this.hasSurrendered = true;
    }
}
