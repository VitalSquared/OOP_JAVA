package ru.nsu.spirin.chess.scene;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.MoveLog;
import ru.nsu.spirin.chess.player.Alliance;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public final class Scene {
    private volatile Board                        board;
    private final    MoveLog                      moveLog;
    private volatile SceneState                   sceneState;
    private          String                       playerName;
    private          Alliance                     playerTeam;
    private final    List<Entry<String, Integer>> scoreTexts;

    public Scene() {
        this.board = null;
        this.sceneState = SceneState.MAIN_MENU;
        this.moveLog = new MoveLog();
        this.moveLog.clear();
        this.scoreTexts = new ArrayList<>();
    }

    public Board getBoard() {
        return this.board;
    }

    public MoveLog getMoveLog() {
        return this.moveLog;
    }

    public List<Entry<String, Integer>> getScoreTexts() {
        return this.scoreTexts;
    }

    public void addScoreText(String text, int score) {
        this.scoreTexts.add(new AbstractMap.SimpleEntry<>(text, score));
    }

    public void setBoard(Board board) {
        if (this.board == board) {
            return;
        }
        this.board = board;
    }

    public void setPlayer(String playerName, Alliance playerTeam) {
        this.playerName = playerName;
        this.playerTeam = playerTeam;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getOpponentName() {
        return "AI";
    }

    public Alliance getPlayerTeam() {
        return this.playerTeam;
    }

    public void setSceneState(SceneState sceneState) {
        this.sceneState = sceneState;
        if (sceneState == SceneState.MAIN_MENU) {
            this.scoreTexts.clear();
        }
    }

    public SceneState getSceneState() {
        return this.sceneState;
    }

    public void destroyScene() {
        this.sceneState = SceneState.NONE;
        this.board = null;
        this.moveLog.clear();
        this.scoreTexts.clear();
    }
}
