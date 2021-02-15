package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.logic.World;

public class DrawCommand implements Command {
    private World world;

    public DrawCommand(World world) {
        this.world = world;
    }

    @Override
    public boolean validateArgs(String[] args) {
        return args.length == 0;
    }

    @Override
    public boolean execute(String[] args) {
        if (world.getIsTurtleDrawing() || !world.isValid()) return false;
        world.setIsTurtleDrawing(this,true);
        return true;
    }
}
