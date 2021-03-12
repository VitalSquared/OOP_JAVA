package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.input.Input;
import ru.nsu.spirin.logoworld.logic.World;
import ru.nsu.spirin.logoworld.math.Direction;

public class MoveCommand implements Command {
    private final World world;
    private final Input input;
    private int steps;

    public MoveCommand(Input input, World world) {
        this.input = input;
        this.world = world;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 2) {
            CommandError.setError("Wrong number of arguments! Use MOVE [U|D|L|R] <steps>");
            return false;
        }
        if (!world.isValid()) {
            CommandError.setError("You have to use {INIT <width> <height> <x> <y>} first!");
            return false;
        }
        if (args[0].length() != 1) {
            CommandError.setError("Wrong direction value. It has to be U, D, L, or R!");
            return false;
        }
        if (Direction.convertCharacterToDirection(args[0].charAt(0)) == Direction.UNKNOWN)  {
            CommandError.setError("Cannot interpret given direction!");
            return false;
        }
        try {
            int s = Integer.parseInt(args[1]);
            if (s <= 0) CommandError.setError("Steps value has to be positive!");
            return s > 0;
        }
        catch (NumberFormatException e) {
            CommandError.setError("Non-integer steps value!");
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= 1 || !world.isValid()) {
            steps = 0;
            if (input.allowJump()) input.setNextCommand(null);
            return false;
        }
        steps++;
        int moveCount = Integer.parseInt(args[1]);
        for (int i = 0; i < moveCount; i++) {
            world.moveTurtle(Direction.convertCharacterToDirection(args[0].charAt(0)));
        }
        return true;
    }
}
