package ru.nsu.spirin.MorseCoder.character;

public class CharacterFrequency implements Comparable<CharacterFrequency> {
    private final char character;
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
        int comp = CharacterCase.CASE_INSENSITIVE_ORDER.compare(this.character, o.character);
        if (comp == 0) return 0;
        int diff = this.frequency - o.frequency;
        return diff != 0 ? diff : comp;
    }
}
