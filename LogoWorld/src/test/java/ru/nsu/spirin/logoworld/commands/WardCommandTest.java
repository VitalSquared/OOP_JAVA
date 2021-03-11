package ru.nsu.spirin.logoworld.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.nsu.spirin.logoworld.logic.World;

class WardCommandTest {

    @Test
    void validateArgs() {
        WardCommand wardCommand;

        World world1 = new World();
        world1.initWorld(10, 10, 2, 2);
        wardCommand = new WardCommand(null, world1);
        assertTrue(wardCommand.validateArgs(new String[]{}));
        assertFalse(wardCommand.validateArgs(new String[]{"Arg0"}));

        World world2 = new World();
        wardCommand = new WardCommand(null, world2);
        assertFalse(wardCommand.validateArgs(new String[]{}));
        assertFalse(wardCommand.validateArgs(new String[]{"Arg0"}));
    }
}