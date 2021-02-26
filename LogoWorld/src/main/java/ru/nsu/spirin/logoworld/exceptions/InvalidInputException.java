package ru.nsu.spirin.logoworld.exceptions;

public class InvalidInputException extends Exception {
    /**
     * When input fails or contains invalid information
     *
     * @param errorMessage message
     */
    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }
}
