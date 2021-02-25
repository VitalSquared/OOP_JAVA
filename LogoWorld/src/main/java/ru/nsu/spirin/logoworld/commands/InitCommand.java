package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.logic.Program;
import ru.nsu.spirin.logoworld.logic.World;

public class InitCommand implements Command {
    private final World world;
    private final Program program;
    private int steps;

    public InitCommand(Program program, World world) {
        this.program = program;
        this.world = world;
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
            program.setNextCommand();
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
