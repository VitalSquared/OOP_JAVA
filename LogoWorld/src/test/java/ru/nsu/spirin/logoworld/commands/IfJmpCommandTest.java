package ru.nsu.spirin.logoworld.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.nsu.spirin.logoworld.logic.World;

class IfJmpCommandTest {

    @Test
    void validateArgs() {
        IfJmpCommand ifJmpCommand;

        World world1 = new World();
        world1.initWorld(10, 10, 2, 2);
        ifJmpCommand = new IfJmpCommand(null, world1);
        assertFalse(ifJmpCommand.validateArgs(new String[]{}));
        assertTrue(ifJmpCommand.validateArgs(new String[]{"(POS_X <= 2 and POS_Y >= 2 and not IS_DRAWING)", "1"}));
        assertFalse(ifJmpCommand.validateArgs(new String[]{"(POSX <= 2 and POSY >= 2 and not ISDRAWING)", "1"}));
        assertTrue(ifJmpCommand.validateArgs(new String[]{"(FIELD_WIDTH <= 2 and FIELD_HEIGHT >= 2 and IS_DRAWING)", "1"}));
    }
}