package ru.nsu.spirin.LogoWorld.commands;

import ru.nsu.spirin.LogoWorld.logic.Executor;
import ru.nsu.spirin.LogoWorld.math.Direction;

public class MoveCommand implements Command {
    private Executor executor;
    private int steps;

    public MoveCommand(Executor executor) {
        this.executor = executor;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 2 || args[0].length() != 1) return false;
        if (Direction.convertCharacterToDirection(args[0].charAt(0)) == Direction.UNKNOWN) return false;
        try {
            int s = Integer.parseInt(args[1]);
            return s > 0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= Integer.parseInt(args[1]) || !executor.isValid()) {
            steps = 0;
            return false;
        }
        steps++;
        executor.move(Direction.convertCharacterToDirection(args[0].charAt(0)));
        return true;
    }
}
