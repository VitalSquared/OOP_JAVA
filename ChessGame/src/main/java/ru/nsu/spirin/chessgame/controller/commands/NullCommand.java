package ru.nsu.spirin.chessgame.controller.commands;

import ru.nsu.spirin.chessgame.controller.Command;
import ru.nsu.spirin.chessgame.scene.Scene;

public class NullCommand extends Command {
    public NullCommand(final Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(final String[] args, final boolean privileged) {
        return false;
    }
}
