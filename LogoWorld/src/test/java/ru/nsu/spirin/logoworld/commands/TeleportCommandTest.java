package ru.nsu.spirin.logoworld.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.nsu.spirin.logoworld.logic.World;

class TeleportCommandTest {

    @Test
    void validateArgs() {
        TeleportCommand tpCommand;

        World world1 = new World();
        world1.initWorld(10, 10, 2, 2);
        tpCommand = new TeleportCommand(null, world1);
        assertFalse(tpCommand.validateArgs(new String[]{}));
        assertFalse(tpCommand.validateArgs(new String[]{"5"}));
        assertTrue(tpCommand.validateArgs(new String[]{"5", "5"}));
        assertFalse(tpCommand.validateArgs(new String[]{"11", "11"}));
        assertFalse(tpCommand.validateArgs(new String[]{"5", "5", "5"}));
        assertFalse(tpCommand.validateArgs(new String[]{"sdf", "fsdg"}));

        World world2 = new World();
        tpCommand = new TeleportCommand(null, world2);
        assertFalse(tpCommand.validateArgs(new String[]{"5", "5"}));
    }
}