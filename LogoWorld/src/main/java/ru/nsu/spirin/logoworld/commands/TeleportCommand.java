package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.input.Input;
import ru.nsu.spirin.logoworld.logic.World;
import ru.nsu.spirin.logoworld.math.Pair;

public class TeleportCommand implements Command {
    private final World world;
    private final Input input;
    private int steps;

    public TeleportCommand(Input input, World world) {
        this.input = input;
        this.world = world;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 2) {
            CommandError.setError("Wrong number of arguments. Use TELEPORT <x> <y>");
            return false;
        }
        if (!world.isValid()) {
            CommandError.setError("You have to use {INIT <width> <height> <x> <y>} first!");
            return false;
        }
        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            Pair fieldSize = world.getFieldSize();
            int width = fieldSize.getFirst();
            int height = fieldSize.getSecond();
            boolean result = 0 <= x && x < width && 0 <= y && y < height;
            if (!result) {
                CommandError.setError("TELEPORT <x> <y>\nX has to be in range [0, width - 1], Y has to be in range [0, height - 1]");
            }
            return result;
        }
        catch (NumberFormatException e) {
            CommandError.setError("Non-integer values!");
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= 1 || !world.isValid()) {
            steps = 0;
            if (input.allowJump()) {
                input.setNextCommand(null);
            }
            return false;
        }
        steps++;
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        world.setTurtlePosition(x, y);
        return true;
    }
}
