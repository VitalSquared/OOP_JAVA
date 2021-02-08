package ru.nsu.spirin.LogoWorld.commands;

import ru.nsu.spirin.LogoWorld.logic.Executor;
import ru.nsu.spirin.LogoWorld.logic.Field;
import ru.nsu.spirin.LogoWorld.math.Direction;
import ru.nsu.spirin.LogoWorld.math.Pair;

public class MoveCommand implements Command {
    private Executor executor;
    private Field field;
    private int steps;

    public MoveCommand(Executor executor, Field field) {
        this.executor = executor;
        this.field = field;
        this.steps = 0;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 2) return false;
        if (Direction.stringToDir(args[0]) == Direction.UNKNOWN) return false;
        try {
            Integer.parseInt(args[1]);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean execute(String[] args) {
        if (steps >= Integer.parseInt(args[1])) {
            steps = 0;
            return false;
        }
        steps++;
        if (executor.getIsDrawing()) {
            Pair execPos = executor.getPosition();
            field.setDrawn(execPos.getX(), execPos.getY(), true);
        }
        executor.move(Direction.stringToDir(args[0]));
        return true;
    }
}
