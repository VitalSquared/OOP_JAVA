package ru.nsu.spirin.chess.view;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.server.ConnectionStatus;
import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.model.scene.SceneState;

public final class ViewChangeWatcher {
    private SceneState  prevSceneState;
    private MatchEntity prevMatchEntity;

    private ConnectionStatus prevConnected;

    private String   prevOpponentName;
    private Alliance prevOpponentTeam;
    private Boolean  prevOpponentReady;

    private String   prevPlayerName;
    private Alliance prevPlayerTeam;
    private Boolean  prevPlayerReady;

    private Board prevBoard;

    public ViewChangeWatcher() {
        nullEverything();
    }

    public void nullEverything() {
        this.prevSceneState = null;
        this.prevMatchEntity = null;
        this.prevConnected = null;
        this.prevOpponentName = null;
        this.prevOpponentTeam = null;
        this.prevOpponentReady = null;
        this.prevPlayerName = null;
        this.prevPlayerTeam = null;
        this.prevPlayerReady = null;
        this.prevBoard = null;
    }

    public void updateContent(Scene scene) {
        if (scene.getActiveGame() == null) nullEverything();
        else {
            MatchEntity matchEntity = scene.getActiveGame();
            this.prevBoard = matchEntity.getBoard();
            this.prevPlayerTeam = matchEntity.getPlayerAlliance();
            this.prevPlayerName = matchEntity.getPlayerName();
            this.prevConnected = matchEntity.connected();
            this.prevOpponentTeam = matchEntity.getOpponentAlliance();
            this.prevOpponentName = matchEntity.getOpponentName();
            this.prevOpponentReady = matchEntity.isOpponentReady();
            this.prevPlayerReady = matchEntity.isPlayerReady();
        }
        this.prevMatchEntity = scene.getActiveGame();
        this.prevSceneState = scene.getSceneState();
    }

    public boolean anyDifference(Scene scene) {
        if (this.prevSceneState != scene.getSceneState()) return true;
        if (this.prevMatchEntity != scene.getActiveGame()) return true;
        if (this.prevMatchEntity == null) return false;

        MatchEntity matchEntity = scene.getActiveGame();

        if (this.prevMatchEntity.getPlayerAlliance() != matchEntity.getPlayerAlliance()) return true;
        if (this.prevConnected != matchEntity.connected()) return true;
        if (this.prevOpponentTeam != matchEntity.getOpponentAlliance()) return true;
        if (!this.prevOpponentName.equals(matchEntity.getOpponentName())) return true;
        if (this.prevOpponentReady != matchEntity.isOpponentReady()) return true;
        if (this.prevPlayerTeam != matchEntity.getPlayerAlliance()) return true;
        if (!this.prevPlayerName.equals(matchEntity.getPlayerName())) return true;
        if (this.prevPlayerReady != matchEntity.isPlayerReady()) return true;

        if (this.prevMatchEntity.getBoard() == null && matchEntity.getBoard() == null) return false;
        return this.prevBoard != matchEntity.getBoard();
    }
}
