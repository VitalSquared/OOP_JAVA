package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.exceptions.CommandsWorkflowException;
import ru.nsu.spirin.logoworld.logic.World;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandFactory {
    private final World world;
    private final Map<String, String> commands;
    private final Map<String, Command> instances;

    public CommandFactory(World world) throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream("commands.properties");
        if (stream == null) throw new IOException("Couldn't locate commands properties file");

        Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        commands = new HashMap<>();
        instances = new HashMap<>();

        for (var cmd : properties.stringPropertyNames()) {
            commands.put(cmd, properties.getProperty(cmd));
        }

        this.world = world;
    }

    /**
     * Gets a command by name
     * @param command command name
     * @return {@code Command} subclass instance
     * @throws CommandsWorkflowException
     */
    public Command getCommand(String command) throws CommandsWorkflowException {
        if (!commands.containsKey(command)) return null;
        if (instances.containsKey(command)) return instances.get(command);

        Command instance;
        try {
            instance = (Command) Class.forName(commands.get(command)).getConstructor(World.class).newInstance(world);
            instances.put(command, instance);
        }
        catch (Exception e) {
            throw new CommandsWorkflowException(e.getLocalizedMessage());
        }
        return instance;
    }
}
