package ru.nsu.spirin.logoworld.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.nsu.spirin.logoworld.logic.World;

class DrawCommandTest {

    @Test
    void validateArgs() {
        DrawCommand drawCommand;

        World world1 = new World();
        world1.initWorld(10, 10, 2, 2);
        drawCommand = new DrawCommand(null, world1);
        assertTrue(drawCommand.validateArgs(new String[]{}));
        assertFalse(drawCommand.validateArgs(new String[]{"Arg0"}));

        World world2 = new World();
        drawCommand = new DrawCommand(null, world2);
        assertFalse(drawCommand.validateArgs(new String[]{}));
        assertFalse(drawCommand.validateArgs(new String[]{"Arg0"}));
    }
}