package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.logic.Executor;

public class DrawCommand implements Command {
    private Executor executor;

    public DrawCommand(Executor executor) {
        this.executor = executor;
    }

    @Override
    public boolean validateArgs(String[] args) {
        return args.length == 0;
    }

    @Override
    public boolean execute(String[] args) {
        if (executor.getIsDrawing() || !executor.isValid()) return false;
        executor.setIsDrawing(true);
        return true;
    }
}
