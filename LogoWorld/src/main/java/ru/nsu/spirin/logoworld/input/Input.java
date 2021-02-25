package ru.nsu.spirin.logoworld.input;

public interface Input {
    String nextCommand();
    void setNextCommand();
    void setNextCommand(int nextCommand);
    boolean isFinished();
    boolean allowJump();
    void close();
}
