package ru.nsu.spirin.logoworld.exceptions;

public class RenderException extends Exception {
    /**
     * When you have rendering errors
     *
     * @param errorMessage message
     */
    public RenderException(String errorMessage) {
        super(errorMessage);
    }
}
