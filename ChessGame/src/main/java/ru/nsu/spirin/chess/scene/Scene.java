package ru.nsu.spirin.chess.scene;

import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.communication.Client;
import ru.nsu.spirin.chess.communication.GameEntity;
import ru.nsu.spirin.chess.communication.Local;
import ru.nsu.spirin.chess.communication.Server;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.utils.ThreadUtils;

import java.io.IOException;

public final class Scene {
    private volatile GameEntity activeGame;
    private volatile SceneState sceneState;

    public Scene() {
        this.activeGame = null;
        this.sceneState = SceneState.MAIN_MENU;

        ThreadUtils.submitThread(
            new Thread(() -> {
                while (sceneState != SceneState.NONE) {
                    if (activeGame != null && activeGame.getBoard() != null && !BoardUtils.isEndGame(activeGame.getBoard())) {
                        setSceneState(SceneState.BOARD_MENU);
                    }
                }
            })
        );
    }

    public GameEntity getActiveGame() {
        return this.activeGame;
    }

    public void startLocalGame(String playerName, Alliance playerTeam) {
        this.activeGame = new Local(playerName, playerTeam);
    }

    public void hostServerGame(String ipAddress, int port, String playerName) throws IOException {
        try {
            this.activeGame = new Server(ipAddress, port, playerName);
        }
        catch (IOException e) {
            this.activeGame = null;
            throw new IOException(e);
        }
        setSceneState(SceneState.CONNECTION_MENU);
    }

    public void joinServerGame(String ipAddress, int port, String playerName) throws IOException {
        try {
            this.activeGame = new Client(ipAddress, port, playerName);
        }
        catch (IOException e) {
            this.activeGame = null;
            throw new IOException(e);
        }
        setSceneState(SceneState.CONNECTION_MENU);
    }

    public void setSceneState(SceneState sceneState) {
        this.sceneState = sceneState;
    }

    public SceneState getSceneState() {
        return this.sceneState;
    }
}
