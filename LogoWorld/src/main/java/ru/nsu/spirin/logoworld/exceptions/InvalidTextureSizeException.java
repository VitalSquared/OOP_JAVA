package ru.nsu.spirin.logoworld.exceptions;

public class InvalidTextureSizeException extends Exception {
    /**
     * When you have invalid texture size
     * @param errorMessage message
     */
    public InvalidTextureSizeException(String errorMessage) {
        super(errorMessage);
    }
}
