package ru.nsu.spirin.chess.model.match;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.move.MoveLog;
import ru.nsu.spirin.chess.model.move.MoveTransition;
import ru.nsu.spirin.chess.model.move.PawnPromotion;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.match.server.ConnectionStatus;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public abstract class MatchEntity {
    private volatile Board   board;
    private final    MoveLog moveLog;

    private final    String   playerName;
    private volatile Alliance playerAlliance;

    private final List<Entry<String, Integer>> scoreTexts;

    protected MatchEntity(String playerName) {
        this.board = null;
        this.moveLog = new MoveLog();
        this.scoreTexts = new ArrayList<>();
        this.playerName = playerName;
    }

    public abstract String getOpponentName();

    public abstract Alliance getOpponentAlliance();

    public abstract boolean isOpponentReady();

    public abstract boolean isPlayerReady();

    public abstract void setPlayerReady(boolean playerReady);

    public abstract void makeMove(Move move, MoveTransition transition);

    public abstract ConnectionStatus connected();

    public abstract void closeConnection();

    public Board getBoard() {
        return this.board;
    }

    public MoveLog getMoveLog() {
        return this.moveLog;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Alliance getPlayerAlliance() {
        return this.playerAlliance;
    }

    public List<Entry<String, Integer>> getScoreTexts() {
        return this.scoreTexts;
    }

    protected void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayerAlliance(Alliance playerTeam) {
        this.playerAlliance = playerTeam;
    }

    public void addScoreText(String text, int score) {
        this.scoreTexts.add(new AbstractMap.SimpleEntry<>(text, score));
    }

    protected void calculateScore(Move move) {
        try {
            if (move.getMovedPiece() != null && move.getMovedPiece().getAlliance() == getPlayerAlliance()) {
                if (move.isAttack()) addScoreText("Piece attack: " + move.getAttackedPiece().getType().toString(), move.getAttackedPiece().getType().getPieceValue());
                if (move.isCastlingMove()) addScoreText("Castling", 100);
                if (move instanceof PawnPromotion) addScoreText("Promoted pawn", 200);
            }
        }
        catch (Exception ignored) {
        }
    }
}
