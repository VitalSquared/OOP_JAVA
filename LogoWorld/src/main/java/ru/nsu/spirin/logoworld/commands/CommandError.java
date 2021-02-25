package ru.nsu.spirin.logoworld.commands;

public class CommandError {
    static String error = null;

    static void setError(String msg) {
        error = msg;
    }
    public static String getError() { return error; }
}
