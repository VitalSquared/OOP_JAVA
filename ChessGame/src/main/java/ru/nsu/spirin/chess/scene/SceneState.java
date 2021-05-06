package ru.nsu.spirin.chess.scene;

public enum SceneState {
    NONE(null),
    MAIN_MENU(null),
    NEW_GAME_MENU(MAIN_MENU),
    CONNECTION_MENU(NEW_GAME_MENU),
    BOARD_MENU(null),
    RESULTS_MENU(MAIN_MENU),
    HIGH_SCORES_MENU(MAIN_MENU),
    ABOUT_MENU(MAIN_MENU);

    private final SceneState prevScene;

    SceneState(SceneState prevScene) {
        this.prevScene = prevScene;
    }

    public SceneState getPrevScene() {
        return prevScene;
    }
}
