package ru.nsu.spirin.chess.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class Factory<Type> {
    private final Map<String, String> mapNameToPath;
    private final Map<String, Type> mapNameToInstance;
    private final Type              nullTypeCache;

    protected Factory(String pathToProperties, Type nullTypeCache) throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream(pathToProperties);
        if (stream == null) throw new IOException("Couldn't locate " + pathToProperties + " properties file");

        Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        mapNameToPath = new HashMap<>();
        mapNameToInstance = new HashMap<>();
        for (var cmd : properties.stringPropertyNames()) {
            mapNameToPath.put(cmd, properties.getProperty(cmd));
        }

        this.nullTypeCache = nullTypeCache;
    }

    protected abstract Type createInstance(String path) throws FactoryException;

    public Type get(String name) {
        name = name.toUpperCase();
        String path = mapNameToPath.getOrDefault(name, null);
        if (path == null) return nullTypeCache;

        Type instance = mapNameToInstance.getOrDefault(name, null);
        if (instance == null) {
            try {
                instance = createInstance(path);
                mapNameToInstance.put(name, instance);
            }
            catch (Exception ignored) {
                return nullTypeCache;
            }
        }
        return instance;
    }
}
