package ru.nsu.spirin.chessgame.controller;

import ru.nsu.spirin.chessgame.controller.commands.NullCommand;
import ru.nsu.spirin.chessgame.scene.Scene;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class CommandFactory {
    private final Scene                scene;
    private final Map<String, String>  commands;
    private final Map<String, Command> instances;
    private final Command              nullCommandCache;

    public CommandFactory(final Scene scene) throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream("commands.properties");
        if (stream == null) {
            throw new IOException("Couldn't locate commands properties file");
        }

        Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        commands = new HashMap<>();
        instances = new HashMap<>();

        for (var cmd : properties.stringPropertyNames()) {
            commands.put(cmd, properties.getProperty(cmd));
        }

        this.scene = scene;
        this.nullCommandCache = new NullCommand(scene);
    }

    public Command getCommand(final String command) {
        if (!commands.containsKey(command)) return nullCommandCache;
        if (instances.containsKey(command)) return instances.get(command);

        Command instance;
        try {
            instance = (Command) Class.forName(commands.get(command)).getConstructor(Scene.class).newInstance(scene);
            instances.put(command, instance);
        }
        catch (Exception e) {
            return nullCommandCache;
        }
        return instance;
    }
}
