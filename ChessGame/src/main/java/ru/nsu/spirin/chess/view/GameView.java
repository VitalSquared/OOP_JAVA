package ru.nsu.spirin.chess.view;

import ru.nsu.spirin.chess.communication.ConnectionStatus;
import ru.nsu.spirin.chess.communication.GameEntity;
import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public abstract class GameView {
    private final Scene scene;
    private final Controller controller;

    private SceneState prevSceneState;
    private GameEntity prevGameEntity;

    private ConnectionStatus prevConnected;

    private String prevOpponentName;
    private Alliance prevOpponentTeam;
    private Boolean prevOpponentReady;

    private String prevPlayerName;
    private Alliance prevPlayerTeam;
    private Boolean prevPlayerReady;

    protected GameView(Scene scene, Controller controller) {
        this.scene = scene;
        this.controller = controller;
        this.prevSceneState = null;
        this.prevGameEntity = null;

        this.prevConnected = null;

        this.prevOpponentName = null;
        this.prevOpponentTeam = null;
        this.prevOpponentReady = null;

        this.prevPlayerName = null;
        this.prevPlayerTeam = null;
        this.prevPlayerReady = null;
    }

    protected Scene getScene() {
        return this.scene;
    }

    protected Controller getController() {
        return this.controller;
    }

    public abstract void render();

    public abstract void close();

    protected boolean viewChanged() {
        boolean changed = activeGameChanged() || sceneStateChanged();
        if (!changed) return false;
        this.prevGameEntity = getScene().getActiveGame();
        this.prevSceneState = getScene().getSceneState();
        try {
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            this.prevConnected = networkEntity.connected();

            this.prevOpponentTeam = networkEntity.getOpponentTeam();
            this.prevOpponentName = networkEntity.getOpponentName();
            this.prevOpponentReady = networkEntity.isOpponentReady();

            this.prevPlayerTeam = networkEntity.getPlayerAlliance();
            this.prevPlayerName = networkEntity.getPlayerName();
            this.prevPlayerReady = networkEntity.isPlayerReady();
        }
        catch (Exception ignored) {

            this.prevConnected = null;

            this.prevOpponentName = null;
            this.prevOpponentTeam = null;
            this.prevOpponentReady = null;

            this.prevPlayerName = null;
            this.prevPlayerTeam = null;
            this.prevPlayerReady = null;
        }
        return true;
    }

    private boolean activeGameChanged() {
        if (this.prevGameEntity != scene.getActiveGame()) return true;
        if (this.prevGameEntity == null) return false;
        if (this.prevGameEntity.getPlayerAlliance() != scene.getActiveGame().getPlayerAlliance()) return true;
        if (this.prevGameEntity instanceof NetworkEntity) {
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (this.prevConnected != networkEntity.connected()) return true;

            if (this.prevOpponentTeam != networkEntity.getOpponentTeam()) return true;
            if(!this.prevOpponentName.equals(networkEntity.getOpponentName())) return true;
            if (this.prevOpponentReady != networkEntity.isOpponentReady()) return true;

            if (this.prevPlayerTeam != networkEntity.getPlayerAlliance()) return true;
            if(!this.prevPlayerName.equals(networkEntity.getPlayerName())) return true;
            if (this.prevPlayerReady != networkEntity.isPlayerReady()) return true;
        }
        if (this.prevGameEntity.getBoard() == null && scene.getActiveGame().getBoard() == null) return false;
        if (this.prevGameEntity.getBoard() != scene.getActiveGame().getBoard()) return true;
        return this.prevGameEntity.getBoard().hashCode() != scene.getActiveGame().getBoard().hashCode();
    }

    private boolean sceneStateChanged() {
        return this.prevSceneState != getScene().getSceneState();
    }
}
