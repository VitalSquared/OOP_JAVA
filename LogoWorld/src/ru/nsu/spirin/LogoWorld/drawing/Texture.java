package ru.nsu.spirin.LogoWorld.drawing;

public class Texture {
    private int size;
    private String texture;
    private String color;

    public Texture(String texture, String color) {
        this.texture = texture;
        this.color = color;
        this.size = (int) Math.sqrt(texture.length());
        assert this.size * this.size == texture.length();
    }

    public String getPixel(int r, int c) {
        return this.texture.charAt(r * size + c) + "";
    }

    public int getSize() {
        return size;
    }
}
