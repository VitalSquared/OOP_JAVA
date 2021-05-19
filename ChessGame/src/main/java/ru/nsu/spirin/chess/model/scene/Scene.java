package ru.nsu.spirin.chess.model.scene;

import ru.nsu.spirin.chess.model.match.Client;
import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.model.match.Local;
import ru.nsu.spirin.chess.model.player.Alliance;

import java.io.IOException;

public final class Scene {
    private volatile MatchEntity activeGame;
    private volatile SceneState  sceneState;

    public Scene() {
        this.activeGame = null;
        this.sceneState = SceneState.MAIN_MENU;
    }

    public MatchEntity getActiveGame() {
        return this.activeGame;
    }

    public SceneState getSceneState() {
        return this.sceneState;
    }

    public void setSceneState(SceneState sceneState) {
        this.sceneState = sceneState;
    }

    public void startLocalGame(String playerName, Alliance playerTeam) {
        this.activeGame = new Local(playerName, playerTeam);
    }

    public void joinServerGame(String ipAddress, int port, String playerName) throws IOException {
        try {
            this.activeGame = new Client(this, ipAddress, port, playerName);
        }
        catch (IOException e) {
            this.activeGame = null;
            throw new IOException(e);
        }
        setSceneState(SceneState.CONNECTION_MENU);
    }
}
