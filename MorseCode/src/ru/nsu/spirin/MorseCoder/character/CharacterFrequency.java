package ru.nsu.spirin.MorseCoder.character;

public class CharacterFrequency implements Comparable<CharacterFrequency> {
    private char character;
    private int frequency;

    public CharacterFrequency(char ch) {
        this.character = ch;
        this.frequency = 1;
    }

    public char getChar() {
        return this.character;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void increaseFrequency() {
        this.frequency++;
    }

    @Override
    public int compareTo(CharacterFrequency o) {
        if (this.character == o.character) return 0;
        int diff = this.frequency - o.frequency;
        if (diff != 0) return diff;
        else return this.character - o.character;
    }
}
