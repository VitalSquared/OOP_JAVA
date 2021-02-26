package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.input.Input;
import ru.nsu.spirin.logoworld.logic.World;

public class DrawCommand implements Command {
    private final World world;
    private final Input input;

    public DrawCommand(Input input, World world) {
        this.input = input;
        this.world = world;
    }

    @Override
    public boolean validateArgs(String[] args) {
        if (args.length != 0) {
            CommandError.setError("Use DRAW without any arguments.");
            return false;
        }
        if (!world.isValid()) {
            CommandError.setError("You have to use {INIT <width> <height> <x> <y>} first!");
            return false;
        }
        return true;
    }

    @Override
    public boolean execute(String[] args) {
        if (world.getIsTurtleDrawing() || !world.isValid()) {
            if (input.allowJump()) {
                input.setNextCommand(null);
            }
            return false;
        }
        world.setIsTurtleDrawing(true);
        return true;
    }
}
