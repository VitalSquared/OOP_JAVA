package ru.nsu.spirin.chessgame.scene;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.move.MoveLog;

import java.util.ArrayList;
import java.util.List;

public final class Scene {
    private       Board         board;
    private final MoveLog       moveLog;
    private       SceneState    sceneState;
    private final List<Integer> lastBoardsHash;
    private       int           miniMaxDepth;

    public Scene() {
        this.board = null;
        this.sceneState = SceneState.MAIN_MENU;
        this.moveLog = new MoveLog();
        this.moveLog.clear();
        this.lastBoardsHash = new ArrayList<>();
        this.miniMaxDepth = 4;
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
        addToBoardHashes(this.board);
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
        this.lastBoardsHash.clear();
    }

    private void addToBoardHashes(final Board board) {
        if (board == null) {
            return;
        }

        int curHash = board.hashCode();
        for (int hash : lastBoardsHash) {
            if (curHash == hash) {
                miniMaxDepth = Math.min(miniMaxDepth + 1, 10);
                lastBoardsHash.clear();
                break;
            }
        }
        if (lastBoardsHash.size() >= 10) {
            lastBoardsHash.remove(0);
        }
        lastBoardsHash.add(curHash);
    }

    public int getMiniMaxDepth() {
        return this.miniMaxDepth;
    }
}
