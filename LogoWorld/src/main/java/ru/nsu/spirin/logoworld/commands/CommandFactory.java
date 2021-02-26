package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.input.Input;
import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.exceptions.CommandFactoryException;
import ru.nsu.spirin.logoworld.logic.World;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandFactory {
    private static final Logger logger = Logger.getLogger(CommandFactory.class);

    private final World world;
    private final Input input;
    private final Map<String, String> commands;
    private final Map<String, Command> instances;

    public CommandFactory(Input input, World world) throws IOException {
        logger.debug("Command Factory initialization.");
        InputStream stream = ClassLoader.getSystemResourceAsStream("commands.properties");
        if (stream == null) {
            logger.error("No 'commands.properties' file found!");
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

        this.world = world;
        this.input = input;
    }

    /**
     * Gets a command by name
     *
     * @param command command name
     * @return {@code Command} subclass instance
     * @throws CommandFactoryException if command factory fails
     */
    public Command getCommand(String command) throws CommandFactoryException {
        if (!commands.containsKey(command)) {
            CommandError.setError("Unknown command: " + command);
            logger.debug("Unknown command: " + command);
            return null;
        }
        if (instances.containsKey(command)) return instances.get(command);

        Command instance;
        try {
            logger.debug("Command request: " + command);
            logger.debug("Creating instance of class: " + commands.get(command));
            instance = (Command) Class.forName(commands.get(command)).getConstructor(Input.class, World.class).newInstance(input, world);
            instances.put(command, instance);
            logger.debug("Successfully created instance of class.");
        }
        catch (Exception e) {
            logger.error("Error encountered while creating new command instance:");
            logger.error(e.getLocalizedMessage());
            throw new CommandFactoryException(e.getLocalizedMessage());
        }
        return instance;
    }
}
