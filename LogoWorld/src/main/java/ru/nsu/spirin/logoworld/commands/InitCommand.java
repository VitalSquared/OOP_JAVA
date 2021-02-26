package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.input.Input;
import ru.nsu.spirin.logoworld.logic.World;

public class InitCommand implements Command {
    private final World world;
    private final Input input;
    private int steps;

    public InitCommand(Input input, World world) {
        this.input = input;
        this.world = world;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 4) {
            CommandError.setError("Wrong number of arguments. Use INIT <width> <height> <x> <y>");
            return false;
        }
        try {
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            int x = Integer.parseInt(args[2]);
            int y = Integer.parseInt(args[3]);
            boolean result = 0 <= x && x < width && 0 <= y && y < height;
            if (!result) {
                CommandError.setError("INIT <width> <height> <x> <y>\n<width> and <height> has to be at least 1\n<x> has to be in range [0, width - 1], <y> has to be in range [0, height - 1].");
            }
            return result;
        }
        catch (NumberFormatException e) {
            CommandError.setError("Non-integer arguments.");
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= 1) {
            steps = 0;
            if (input.allowJump()) input.setNextCommand(null);
            return false;
        }
        steps++;
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int x = Integer.parseInt(args[2]);
        int y = Integer.parseInt(args[3]);
        world.initWorld(width, height, x, y);
        return true;
    }
}
