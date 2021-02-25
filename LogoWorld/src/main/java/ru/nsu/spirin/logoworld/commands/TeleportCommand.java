package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.logic.Program;
import ru.nsu.spirin.logoworld.logic.World;
import ru.nsu.spirin.logoworld.math.Pair;

public class TeleportCommand implements Command {
    private final World world;
    private final Program program;
    private int steps;

    public TeleportCommand(Program program, World world) {
        this.program = program;
        this.world = world;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 2) return false;
        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            Pair fieldSize = world.getFieldSize();;
            int width = fieldSize.getFirst();
            int height = fieldSize.getSecond();
            return 0 <= x && x < width && 0 <= y && y < height;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= 1 || !world.isValid()) {
            steps = 0;
            program.setNextCommand();
            return false;
        }
        steps++;
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        world.setTurtlePosition(y, x);
        return true;
    }
}
