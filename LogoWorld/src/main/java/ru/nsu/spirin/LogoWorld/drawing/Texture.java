package ru.nsu.spirin.LogoWorld.drawing;

import ru.nsu.spirin.LogoWorld.exceptions.InvalidTextureSizeException;

public class Texture {
    private final int size;
    private final String texture;
    private String color;

    /**
     * Creates texture with given {@code String} representation and color.
     * @param texture string representation of texture
     * @param color string representation of color in console
     * @throws InvalidTextureSizeException if texture string length was not a square of integer number
     */
    public Texture(String texture, String color) throws InvalidTextureSizeException {
        this.texture = texture;
        this.color = color;
        this.size = (int) Math.sqrt(texture.length());
        if (this.size * this.size != texture.length()) {
            throw new InvalidTextureSizeException("Texture size was not a square of integer number");
        }
    }

    /**
     * Gets pixel as char
     * @param r row of texture
     * @param c column of texture
     * @return String representation of pixel with color
     */
    public String getPixel(int r, int c) {
        return this.texture.charAt(r * size + c) + "";
    }

    /**
     * Gets size of texture, a square root of string length
     * @return size of texture
     */
    public int getSize() {
        return size;
    }
}
