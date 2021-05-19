package ru.nsu.spirin.chess.factory;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.controller.commands.NullCommand;
import ru.nsu.spirin.chess.model.scene.Scene;

import java.io.IOException;

public final class CommandFactory extends Factory<Command> {
    private final Scene scene;

    public CommandFactory(Scene scene) throws IOException {
        super("commands.properties", new NullCommand(scene));
        this.scene = scene;
    }

    @Override
    protected Command createInstance(String path) throws FactoryException {
        try {
            return (Command) Class.forName(path).getConstructor(Scene.class).newInstance(scene);
        }
        catch (Exception e) {
            throw new FactoryException(e);
        }
    }
}
