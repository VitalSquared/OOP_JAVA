package ru.nsu.spirin.chessgame.scene;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.MoveLog;

public final class Scene {
    private       Board      board;
    private final MoveLog    moveLog;
    private       SceneState sceneState;

    public Scene() {
        this.board = null;
        this.sceneState = SceneState.MAIN_MENU;
        this.moveLog = new MoveLog();
        this.moveLog.clear();
    }

    public Board getBoard() {
        return this.board;
    }

    public MoveLog getMoveLog() {
        return this.moveLog;
    }

    public void setBoard(final Board board) {
        if (this.board == board) {
            return;
        }

        this.board = board;
        if (board == null) {
            this.sceneState = SceneState.MAIN_MENU;
            this.moveLog.clear();
        }
        else {
            this.sceneState = SceneState.GAME;
        }
    }

    public SceneState getSceneState() {
        return this.sceneState;
    }

    public void destroyScene() {
        this.sceneState = SceneState.DESTROY_SELF;
        this.board = null;
        this.moveLog.clear();
    }
}
