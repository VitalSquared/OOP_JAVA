package ru.nsu.spirin.chess.communication;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveLog;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.move.PawnPromotion;
import ru.nsu.spirin.chess.player.Alliance;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public abstract class GameEntity {
    private volatile Board                        board;
    private final    MoveLog                      moveLog;
    private          String                       playerName;
    private          Alliance                     playerTeam;
    private final    List<Entry<String, Integer>> scoreTexts;

    protected GameEntity() {
        this.board = null;
        this.moveLog = new MoveLog();
        this.scoreTexts = new ArrayList<>();
    }

    public abstract String getOpponentName();

    public abstract Alliance getOpponentTeam();

    public abstract void makeMove(Move move, MoveTransition transition);

    public Board getBoard() {
        return this.board;
    }

    protected void setBoard(Board board) {
        this.board = board;
    }

    public MoveLog getMoveLog() {
        return this.moveLog;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Alliance getPlayerAlliance() {
        return this.playerTeam;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerTeam(Alliance playerTeam) {
        this.playerTeam = playerTeam;
    }

    public List<Entry<String, Integer>> getScoreTexts() {
        return this.scoreTexts;
    }

    public void addScoreText(String text, int score) {
        this.scoreTexts.add(new AbstractMap.SimpleEntry<>(text, score));
    }

    protected void calculateScore(Move move) {
        if (move.isAttack()) {
            addScoreText("Piece attack: " + move.getAttackedPiece().getType().toString(), move.getAttackedPiece().getType().getPieceValue());
        }
        if (move.isCastlingMove()) {
            addScoreText("Castling",  100);
        }
        if (move instanceof PawnPromotion) {
            addScoreText("Promoted pawn",  200);
        }
    }
}
