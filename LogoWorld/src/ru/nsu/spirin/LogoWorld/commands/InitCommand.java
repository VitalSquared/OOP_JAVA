package ru.nsu.spirin.LogoWorld.commands;

import ru.nsu.spirin.LogoWorld.logic.Executor;
import ru.nsu.spirin.LogoWorld.logic.Field;

public class InitCommand implements Command {
    private Executor executor;
    private Field field;
    private int steps;

    public InitCommand(Executor executor, Field field) {
        this.executor = executor;
        this.field = field;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 4) return false;
        try {
            int w = Integer.parseInt(args[0]);
            int h = Integer.parseInt(args[1]);
            int x = Integer.parseInt(args[2]);
            int y = Integer.parseInt(args[3]);
            return true;
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
        field.setSize(width, height);
        executor.setPosition(y, x);
        return true;
    }
}
