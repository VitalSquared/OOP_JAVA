package ru.nsu.spirin.logoworld.input;

import ru.nsu.spirin.logoworld.exceptions.InvalidInputException;

public interface Input {
    /**
     * Check if input is finished
     *
     * @return true if finished
     */
    boolean isFinished();

    /**
     * Check if input allows jumping through previous/next commands
     *
     * @return true if allowed
     */
    boolean allowJump();

    /**
     * Get next command
     *
     * @return next command
     */
    String nextCommand() throws InvalidInputException;

    /**
     * Set next command.
     *
     * @param commandPosition use null to switch to next command
     */
    void setNextCommand(Integer commandPosition);
}
