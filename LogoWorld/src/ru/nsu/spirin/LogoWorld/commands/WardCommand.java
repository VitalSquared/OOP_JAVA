package ru.nsu.spirin.LogoWorld.commands;

import ru.nsu.spirin.LogoWorld.logic.Executor;

public class WardCommand implements Command {
    private Executor executor;

    public WardCommand(Executor executor) {
        this.executor = executor;
    }

    @Override
    public boolean validateArgs(String[] args) {
        return args.length == 0;
    }

    @Override
    public boolean execute(String[] args) {
        if (!executor.getIsDrawing()) return false;
        executor.setIsDrawing(false);
        return true;
    }
}
