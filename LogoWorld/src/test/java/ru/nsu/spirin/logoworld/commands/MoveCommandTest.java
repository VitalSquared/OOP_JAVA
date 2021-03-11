package ru.nsu.spirin.logoworld.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.nsu.spirin.logoworld.logic.World;

class MoveCommandTest {

    @Test
    void validateArgs() {
        MoveCommand moveCommand;

        World world1 = new World();
        world1.initWorld(10, 10, 2, 2);
        moveCommand = new MoveCommand(null, world1);
        assertFalse(moveCommand.validateArgs(new String[]{}));
        assertFalse(moveCommand.validateArgs(new String[]{"U"}));
        assertTrue(moveCommand.validateArgs(new String[]{"U", "10"}));
        assertFalse(moveCommand.validateArgs(new String[]{"Z", "10"}));
        assertFalse(moveCommand.validateArgs(new String[]{"R", "-1"}));
        assertFalse(moveCommand.validateArgs(new String[]{"D", "0"}));

        World world2 = new World();
        moveCommand = new MoveCommand(null, world2);
        assertFalse(moveCommand.validateArgs(new String[]{"U", "10"}));
    }
}