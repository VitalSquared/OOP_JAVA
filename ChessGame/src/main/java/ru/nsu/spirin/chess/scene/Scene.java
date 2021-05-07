package ru.nsu.spirin.chess.scene;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.move.MoveLog;

public final class Scene {
    private          Board         board;
    private final    MoveLog       moveLog;
    private volatile SceneState    sceneState;

    public Scene() {
        this.board = null;
        this.sceneState = SceneState.MAIN_MENU;
        this.moveLog = new MoveLog();
        this.moveLog.clear();

        new Thread(() -> {
            while (sceneState != SceneState.NONE) {
                try {
                    if (sceneState == SceneState.BOARD_MENU) {
                        if (board.getCurrentPlayer().isInCheckMate() || board.getCurrentPlayer().isInStaleMate() || board.getCurrentPlayer().hasSurrendered()) {
                            Thread.sleep(1000);
                            sceneState = SceneState.RESULTS_MENU;
                        }
                    }
                }
                catch (Exception ignored) {}
            }
        }).start();
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
    }

    public void setSceneState(SceneState sceneState) {
        this.sceneState = sceneState;
    }

    public SceneState getSceneState() {
        return this.sceneState;
    }

    public void destroyScene() {
        this.sceneState = SceneState.NONE;
        this.board = null;
        this.moveLog.clear();
    }
}
