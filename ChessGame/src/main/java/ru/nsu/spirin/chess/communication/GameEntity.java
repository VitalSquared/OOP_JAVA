package ru.nsu.spirin.chess.communication;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.MoveLog;
import ru.nsu.spirin.chess.player.Alliance;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;

public abstract class GameEntity {
    private volatile Board                        board;
    private          MoveLog                      moveLog;
    private          String                       playerName;
    private          Alliance                     playerTeam;
    private          List<Entry<String, Integer>> scoreTexts;

    public abstract String getOpponentName();

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
        return this.playerTeam;
    }

    public void setPlayer(String playerName, Alliance playerTeam) {
        this.playerName = playerName;
        this.playerTeam = playerTeam;
    }

    public List<Entry<String, Integer>> getScoreTexts() {
        return this.scoreTexts;
    }

    public void addScoreText(String text, int score) {
        this.scoreTexts.add(new AbstractMap.SimpleEntry<>(text, score));
    }
}
