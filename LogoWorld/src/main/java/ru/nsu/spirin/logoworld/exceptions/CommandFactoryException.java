package ru.nsu.spirin.logoworld.exceptions;

public class CommandFactoryException extends Exception {
    /**
     * When command factory reflection fails
     *
     * @param errorMessage message
     */
    public CommandFactoryException(String errorMessage) {
        super(errorMessage);
    }
}
