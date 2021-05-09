package ru.nsu.spirin.chess.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveStatus;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.move.PawnPromotion;
import ru.nsu.spirin.chess.move.ResignMove;
import ru.nsu.spirin.chess.pieces.King;
import ru.nsu.spirin.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    private final Board   board;
    private final King    playerKing;
    private final boolean isInCheck;
    private       boolean resigned;
    private       int     promotedPawns;

    private final Collection<Move> legalMoves;

    protected Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getCoordinate(), opponentMoves).isEmpty();
        this.promotedPawns = 0;
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

    private boolean isMoveLegal(Move move, boolean checkForTurn) {
        return (!checkForTurn || board.getCurrentPlayer() == this) && this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isResigned() {
        return this.resigned;
    }

    public int getPromotedPawns() {
        return this.promotedPawns;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastleCapable();
    }

    public void resign() {
        this.resigned = true;
    }

    public MoveTransition makeMove(Move move) {
        return makeMovePrivileged(move, true);
    }

    private MoveTransition makeMovePrivileged(Move move, boolean checkForTurn) {
        if (move instanceof ResignMove) {
            return new MoveTransition(move.execute(), move, MoveStatus.DONE);
        }
        if (!isMoveLegal(move, checkForTurn)) return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        Board transitionBoard = move.execute();
        Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getCoordinate(), transitionBoard.getCurrentPlayer().getLegalMoves());
        if (!kingAttacks.isEmpty()) return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        if (move instanceof PawnPromotion) this.promotedPawns++;
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    protected boolean hasEscapeMoves() {
        for (Move move : legalMoves) {
            MoveTransition transition = makeMovePrivileged(move, false);
            if (transition.getMoveStatus().isDone())
                return true;
        }
        return false;
    }

    protected boolean hasCastleOpportunities() {
        return !this.isInCheck && !this.playerKing.isCastled() && (this.isKingSideCastleCapable() || this.isQueenSideCastleCapable());
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        List<Move> attackMoves = new ArrayList<>();
        for (Move move : moves) {
            if (piecePosition == move.getDestinationCoordinate()) attackMoves.add(move);
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing() {
        for (Piece piece : getActivePieces()) {
            if (piece.getType().isKing()) return (King) piece;
        }
        throw new RuntimeException("Board doesn't have a king!");
    }
}
