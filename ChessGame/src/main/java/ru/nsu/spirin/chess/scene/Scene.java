package ru.nsu.spirin.chess.scene;

import ru.nsu.spirin.chess.communication.GameEntity;
import ru.nsu.spirin.chess.communication.Local;
import ru.nsu.spirin.chess.player.Alliance;

public final class Scene {
    private          GameEntity activeGame;
    private volatile SceneState sceneState;

    public Scene() {
        this.activeGame = null;
        this.sceneState = SceneState.MAIN_MENU;
    }

    public GameEntity getActiveGame() {
        return this.activeGame;
    }

    public void startLocalGame(String playerName, Alliance playerTeam) {
        this.activeGame = new Local(playerName, playerTeam);
    }

    public void setSceneState(SceneState sceneState) {
        this.sceneState = sceneState;
    }

    public SceneState getSceneState() {
        return this.sceneState;
    }

    public void destroyScene() {
        this.sceneState = SceneState.NONE;
    }
}
