package ru.nsu.spirin.LogoWorld.commands;

import ru.nsu.spirin.LogoWorld.logic.Executor;
import ru.nsu.spirin.LogoWorld.logic.Field;

public class InitCommand implements Command {
    private Executor executor;
    private int steps;

    public InitCommand(Executor executor) {
        this.executor = executor;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 4) return false;
        try {
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            int x = Integer.parseInt(args[2]);
            int y = Integer.parseInt(args[3]);
            return 0 <= x && x < width && 0 <= y && y < height;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= 1) {
            steps = 0;
            return false;
        }
        steps++;
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int x = Integer.parseInt(args[2]);
        int y = Integer.parseInt(args[3]);
        executor.initField(width, height, x, y);
        return true;
    }
}
