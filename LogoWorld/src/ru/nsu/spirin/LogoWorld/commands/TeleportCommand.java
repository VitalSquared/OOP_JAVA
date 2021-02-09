package ru.nsu.spirin.LogoWorld.commands;

import ru.nsu.spirin.LogoWorld.logic.Executor;

public class TeleportCommand implements Command {
    private Executor executor;
    private int steps;

    public TeleportCommand(Executor executor) {
        this.executor = executor;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 2) return false;
        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int width = executor.getField().getWidth();
            int height = executor.getField().getHeight();
            return 0 <= x && x < width && 0 <= y && y < height;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= 1 || !executor.isValid()) {
            steps = 0;
            return false;
        }
        steps++;
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        executor.setPosition(y, x);
        return true;
    }
}
