package ru.nsu.spirin.chess.factory;

import ru.nsu.spirin.chess.exceptions.FactoryException;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class ImageFactory extends Factory<BufferedImage> {
    public ImageFactory() throws IOException {
        super("images.properties", new BufferedImage(10, 10, Image.SCALE_SMOOTH));
    }

    @Override
    protected BufferedImage createInstance(String path) throws FactoryException {
        try {
            return ImageIO.read(ClassLoader.getSystemResource(path));
        }
        catch (Exception e) {
            throw new FactoryException(e);
        }
    }
}
