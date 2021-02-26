package ru.nsu.spirin.logoworld.commands;

public class CommandError {
    static String error = null;

    static void setError(String msg) {
        error = msg;
    }

    /**
     * Get error of the last executed command.
     *
     * @return message
     */
    public static String getError() {
        return error;
    }
}
