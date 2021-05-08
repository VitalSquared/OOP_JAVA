package ru.nsu.spirin.chess.properties;

import java.io.BufferedOutputStream;
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
        try {
            InputStream stream = null;
            try {
                stream = new FileInputStream("settings.properties");
            }
            catch (Exception e) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("settings.properties"));
                writer.write("LAST_USED_NAME=Name\n");
                writer.close();
            }
            stream = new FileInputStream("settings.properties");
            properties.load(stream);

            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSetting(String name, String value) throws IOException {
        properties.put(name, value);
        OutputStream outputStream = new FileOutputStream("settings.properties");
        properties.store(new OutputStreamWriter(outputStream), "");
        outputStream.close();
    }

    public static String getSettingValue(String name) {
        return (String) properties.getOrDefault(name, "");
    }
}
