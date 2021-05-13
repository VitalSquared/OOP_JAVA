package ru.nsu.spirin.chess.scene;

import ru.nsu.spirin.chess.game.Client;
import ru.nsu.spirin.chess.game.GameEntity;
import ru.nsu.spirin.chess.game.Local;
import ru.nsu.spirin.chess.game.Server;
import ru.nsu.spirin.chess.player.Alliance;

import java.io.IOException;

public final class Scene {
    private volatile GameEntity activeGame;
    private volatile SceneState sceneState;

    public Scene() {
        this.activeGame = null;
        this.sceneState = SceneState.MAIN_MENU;
    }

    public GameEntity getActiveGame() {
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

    public void hostServerGame(String ipAddress, int port, String playerName) throws IOException {
        try {
            this.activeGame = new Server(this, ipAddress, port, playerName);
        }
        catch (IOException e) {
            this.activeGame = null;
            throw new IOException(e);
        }
        setSceneState(SceneState.CONNECTION_MENU);
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
