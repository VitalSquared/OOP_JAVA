package ru.nsu.spirin.chess.view;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.communication.ConnectionStatus;
import ru.nsu.spirin.chess.communication.GameEntity;
import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public final class ViewChangeWatcher {
    private SceneState prevSceneState;
    private GameEntity prevGameEntity;

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
        this.prevGameEntity = null;
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
            this.prevBoard = scene.getActiveGame().getBoard();
            this.prevPlayerTeam = scene.getActiveGame().getPlayerAlliance();
            this.prevPlayerName = scene.getActiveGame().getPlayerName();
            if (scene.getActiveGame() instanceof NetworkEntity) {
                NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
                this.prevConnected = networkEntity.connected();
                this.prevOpponentTeam = networkEntity.getOpponentAlliance();
                this.prevOpponentName = networkEntity.getOpponentName();
                this.prevOpponentReady = networkEntity.isOpponentReady();
                this.prevPlayerReady = networkEntity.isPlayerReady();
            }
        }
        this.prevGameEntity = scene.getActiveGame();
        this.prevSceneState = scene.getSceneState();
    }

    public boolean anyDifference(Scene scene) {
        if (this.prevSceneState != scene.getSceneState()) return true;
        if (this.prevGameEntity != scene.getActiveGame()) return true;
        if (this.prevGameEntity == null) return false;
        if (this.prevGameEntity.getPlayerAlliance() != scene.getActiveGame().getPlayerAlliance()) return true;
        if (this.prevGameEntity instanceof NetworkEntity) {
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (this.prevConnected != networkEntity.connected()) return true;

            if (this.prevOpponentTeam != networkEntity.getOpponentAlliance()) return true;
            if (!this.prevOpponentName.equals(networkEntity.getOpponentName())) return true;
            if (this.prevOpponentReady != networkEntity.isOpponentReady()) return true;

            if (this.prevPlayerTeam != networkEntity.getPlayerAlliance()) return true;
            if (!this.prevPlayerName.equals(networkEntity.getPlayerName())) return true;
            if (this.prevPlayerReady != networkEntity.isPlayerReady()) return true;
        }
        if (this.prevGameEntity.getBoard() == null && scene.getActiveGame().getBoard() == null) return false;
        return this.prevBoard != scene.getActiveGame().getBoard();
    }
}
