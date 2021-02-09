package ru.nsu.spirin.LogoWorld.commands;

import ru.nsu.spirin.LogoWorld.logic.Executor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandFactory {
    Executor executor;
    Map<String, String> commands;
    Map<String, Command> instances;

    public CommandFactory(Executor executor) throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream("properties/commands.properties");
        if (stream == null) throw new IOException("Couldn't locate commands properties file\n");
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

    public Command getCommand(String command) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!commands.containsKey(command)) return null;
        if (instances.containsKey(command)) return instances.get(command);
        Command instance = (Command) Class.forName(commands.get(command)).getConstructor(Executor.class).newInstance(executor);
        instances.put(command, instance);
        return instance;
    }
}
