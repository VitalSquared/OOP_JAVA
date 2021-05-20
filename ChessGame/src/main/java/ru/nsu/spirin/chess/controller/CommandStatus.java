package ru.nsu.spirin.chess.controller;

public enum CommandStatus {
    NORMAL(""),
    INVALID_COMMAND("Not a valid command!\n"),
    INVALID_MENU("This command is not available in the current menu!\n"),
    WRONG_NUMBER_OF_ARGUMENTS("Wrong number of arguments!\n"),
    INVALID_ARGUMENTS("Invalid argument(s)\n"),
    INVALID_IP("Invalid IP address\n"),
    INVALID_PORT("Invalid port\n"),
    INVALID_MOVE("This move can't be done\n"),
    INVALID_TEAM("Team not selected or two players are in the same team\n"),
    EXCEPTION("");

    private final String message;

    CommandStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
