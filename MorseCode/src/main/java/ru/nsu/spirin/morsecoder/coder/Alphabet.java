package ru.nsu.spirin.morsecoder.coder;

import ru.nsu.spirin.morsecoder.character.CharacterCase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Alphabet {
    private final Map<Character, String> encodeMap = new HashMap<>();
    private final Map<String, Character> decodeMap = new HashMap<>();

    private CharacterCase charCase = CharacterCase.UPPER;

    public Alphabet() throws IOException {
        this.setNewAlphabet(ClassLoader.getSystemResourceAsStream("default_alphabet.properties"));
    }

    public void setNewAlphabet(String fileName) throws IOException {
        this.setNewAlphabet(new FileInputStream(fileName));
    }

    private void setNewAlphabet(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);

        encodeMap.clear();
        decodeMap.clear();

        for (String text : properties.stringPropertyNames()) {
            if (text.length() != 1) throw new IOException("File contained invalid content");

            char ch = text.charAt(0);
            String morseCode = properties.getProperty(text);
            encodeMap.put(Character.toLowerCase(ch), morseCode.toLowerCase());
            decodeMap.put(morseCode.toLowerCase(), Character.toLowerCase(ch));
        }
    }

    public void setCharacterCase(CharacterCase newCase) {
        charCase = newCase;
    }

    public String getMorseCodeFromCharacter(char ch) {
        return encodeMap.getOrDefault(Character.toLowerCase(ch), "?");
    }

    public char getCharacterFromMorseCode(String code) {
        return decodeMap.getOrDefault(code.toLowerCase(), '?');
    }

    public String getCasedString(String str) {
        return switch (charCase) {
            case LOWER -> str.toLowerCase();
            case UPPER -> str.toUpperCase();
        };
    }
}
