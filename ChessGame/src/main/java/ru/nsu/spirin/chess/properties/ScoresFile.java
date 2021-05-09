package ru.nsu.spirin.chess.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public final class ScoresFile {
    private static final Properties properties = new Properties();

    static {
        try {
            InputStream stream = null;
            try {
                stream = new FileInputStream("stats.properties");
            }
            catch (Exception e) {
                OutputStream outputStream = new FileOutputStream("stats.properties");
                outputStream.close();
            }
            stream = new FileInputStream("stats.properties");
            properties.load(stream);

            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveScore(String playerName, int score) throws IOException {
        if (properties.containsKey(playerName)) {
            score = Math.max(score, Integer.parseInt((String) properties.get(playerName)));
        }
        properties.put(playerName, score + "");
        OutputStream outputStream = new FileOutputStream("stats.properties");
        properties.store(new OutputStreamWriter(outputStream), "");
        outputStream.close();
    }

    public static Properties getScores() {
        return properties;
    }
}