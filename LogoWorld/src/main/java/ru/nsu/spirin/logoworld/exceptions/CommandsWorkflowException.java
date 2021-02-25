package ru.nsu.spirin.logoworld.exceptions;

public class CommandsWorkflowException extends Exception {
    /**
     * When command factory reflection fails
     * @param errorMessage message
     */
    public CommandsWorkflowException(String errorMessage) {
        super(errorMessage);
    }
}
