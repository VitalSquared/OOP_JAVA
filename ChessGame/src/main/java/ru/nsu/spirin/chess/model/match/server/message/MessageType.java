package ru.nsu.spirin.chess.model.match.server.message;

public enum MessageType {
    PLAYER_NAME,
    PLAYER_TEAM,
    PLAYER_READY,
    PLAYER_MOVE,
    PLAYER_FOUND,
    NEW_BOARD,
    ILLEGAL_MOVE,
    CANCEL_WAITING
}
