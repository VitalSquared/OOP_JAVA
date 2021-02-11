package ru.nsu.spirin.logoworld.commands;

import ru.nsu.spirin.logoworld.logic.Executor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandFactory {
    private final Executor executor;
    private final Map<String, String> commands;
    private final Map<String, Command> instances;

    /**
     * Creates {@code CommandFactory}
     * @param executor executor which will interpret commands
     * @throws IOException if commands properties file is not found or invalid
     */
    public CommandFactory(Executor executor) throws IOException {
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

        this.executor = executor;
    }

    /**
     * Gets a command by name
     * @param command command name
     * @return {@code Command} subclass instance
     * @throws ClassNotFoundException if command class specified in properties is invalid
     * @throws NoSuchMethodException if command constructor failed to be executed
     * @throws IllegalAccessException if an application tries to reflectively create an instance or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor
     * @throws InvocationTargetException is a checked exception that wraps an exception thrown by an invoked method or constructor.
     * @throws InstantiationException if command class failed to be instantiated
     */
    public Command getCommand(String command) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!commands.containsKey(command)) return null;
        if (instances.containsKey(command)) return instances.get(command);

        Command instance = (Command) Class.forName(commands.get(command)).getConstructor(Executor.class).newInstance(executor);
        instances.put(command, instance);
        return instance;
    }
}
