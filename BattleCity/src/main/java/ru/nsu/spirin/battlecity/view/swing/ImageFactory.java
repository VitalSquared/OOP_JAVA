package ru.nsu.spirin.battlecity.view.swing;

import ru.nsu.spirin.battlecity.exceptions.FactoryException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ImageFactory {
    private final Map<String, String> imagePaths;
    private final Map<String, BufferedImage> instances;

    public ImageFactory() throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream("images.properties");
        if (stream == null) {
            throw new IOException("Couldn't locate images properties file");
        }

        Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        imagePaths = new HashMap<>();
        instances = new HashMap<>();

        for (var cmd : properties.stringPropertyNames()) {
            imagePaths.put(cmd, properties.getProperty(cmd));
        }
    }

    public BufferedImage getImage(String image) throws FactoryException {
        if (!imagePaths.containsKey(image)) {
            return null;
        }
        if (instances.containsKey(image)) return instances.get(image);

        BufferedImage instance;
        try {
            InputStream imageStream = ClassLoader.getSystemResourceAsStream(imagePaths.get(image));
            if (imageStream == null) {
                throw new IOException("Couldn't locate image: " + imagePaths.get(image));
            }
            instance = ImageIO.read(imageStream);
            instances.put(image, instance);
        }
        catch (Exception e) {
            throw new FactoryException(e.getLocalizedMessage());
        }
        return instance;
    }
}
