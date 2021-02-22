package ru.nsu.spirin.morsecoder.commands;

import java.io.IOException;

public interface Command {
    boolean execute(String[] args);
}
