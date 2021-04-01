package ru.nsu.spirin.battlecity.model.notification;

public enum Context {
    DESTROY_SELF,
    SHOOT,
    MOVE,
    TO_MAIN_MENU,
    TO_MAP_SELECTION,

    GAME_LOST,
    GAME_WON,

    START_SINGLEPLAYER,
    START_MULTIPLAYER,
    SHOW_HIGH_SCORES,
    SHOW_ABOUT,
    EXIT;
}
