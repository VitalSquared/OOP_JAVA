package ru.nsu.spirin.chess.utils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class SettingsFile {
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            InputStream stream;
            try {
                stream = new FileInputStream("settings.properties");
            }
            catch (Exception e) {
                stream = null;
                BufferedWriter writer = new BufferedWriter(new FileWriter("settings.properties"));
                writer.write("LAST_USED_NAME=Name\n");
                writer.close();
            }
            if (stream == null) stream = new FileInputStream("settings.properties");
            properties.load(stream);

            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSetting(String name, String value) {
        loadProperties();
        properties.put(name, value);
        try (OutputStream outputStream = new FileOutputStream("settings.properties")) {
            properties.store(new OutputStreamWriter(outputStream), "");
        }
        catch (IOException ignored) {
        }
    }

    public static String getSettingValue(String name) {
        try {
            return (String) properties.getOrDefault(name, "");
        }
        catch (Exception ignored) {
            return "";
        }
    }
}
