package ru.nsu.spirin.logoworld.exceptions;

public class InvalidConditionException extends Exception {
    /**
     * When you can't parse condition expression
     *
     * @param errorMessage message
     */
    public InvalidConditionException(String errorMessage) {
        super(errorMessage);
    }
}
