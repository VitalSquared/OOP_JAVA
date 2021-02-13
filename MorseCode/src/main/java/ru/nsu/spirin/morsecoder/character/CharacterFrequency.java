package ru.nsu.spirin.morsecoder.character;

import java.util.Objects;

public class CharacterFrequency {
    private final char character;
    private int frequency;

    public CharacterFrequency(char ch) {
        this.character = Character.toLowerCase(ch);
        this.frequency = 1;
    }

    public char getChar() {
        return this.character;
    }

    public int getFrequency() {
        return this.frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterFrequency that = (CharacterFrequency) o;
        if (this.character == that.character) {
            that.frequency++;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }
}
