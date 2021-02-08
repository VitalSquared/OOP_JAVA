package ru.nsu.spirin.LogoWorld.commands;

public interface Command {
    boolean validateArgs(String[] args);
    boolean execute(String[] args);
}
