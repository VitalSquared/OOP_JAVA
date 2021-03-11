package ru.nsu.spirin.logoworld.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.nsu.spirin.logoworld.logic.World;

class InitCommandTest {

    @Test
    void validateArgs() {
        InitCommand initCommand;

        World world = new World();
        initCommand = new InitCommand(null, world);
        assertTrue(initCommand.validateArgs(new String[]{"10", "10", "2", "2"}));
        assertFalse(initCommand.validateArgs(new String[]{}));
        assertFalse(initCommand.validateArgs(new String[]{"10", "10"}));
        assertFalse(initCommand.validateArgs(new String[]{"10", "10", "-1", "-1"}));
        assertFalse(initCommand.validateArgs(new String[]{"-1", "-1", "0", "0"}));
        assertFalse(initCommand.validateArgs(new String[]{"10", "10", "11", "11"}));
    }
}