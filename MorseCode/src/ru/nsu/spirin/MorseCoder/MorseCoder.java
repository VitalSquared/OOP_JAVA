package ru.nsu.spirin.MorseCoder;

import ru.nsu.spirin.MorseCoder.character.CharacterFrequency;
import ru.nsu.spirin.MorseCoder.character.CharacterCase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class MorseCoder {
    private final char UNKNOWN_CHAR = '#';

    private HashMap<Character, String> encodeMap = null;
    private HashMap<String, Character> decodeMap = null;
    private Set<CharacterFrequency> stats = null;
    private Writer output = null;
    private final CharacterCase charCase = CharacterCase.LOWER;

    /**
     * Creates a MorseCoder, which uses <b>default alphabet</b>
     * @throws IOException thrown when default alphabet file is not found
     */
    public MorseCoder() throws IOException {
        this("default_alphabet.properties");
    }

    /**
     * Creates a MorseCoder, which uses alphabet specified in <b>alphabetFileName</b>.
     * @param alphabetFileName name of the file where morse alphabet is stored.
     * @throws IOException thrown when file is not found or file contains invalid content
     */
    public MorseCoder(String alphabetFileName) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(alphabetFileName));
        Properties properties = new Properties();

        properties.load(reader);
        reader.close();

        encodeMap = new HashMap<>();
        decodeMap = new HashMap<>();

        for (String text : properties.stringPropertyNames()) {
            if (text.length() != 1) throw new IOException("File contained invalid content");

            char ch = getCasedCharacter(text.charAt(0));
            String morseCode = properties.getProperty(text);
            encodeMap.put(ch, morseCode);
            decodeMap.put(morseCode, ch);
        }

        output = new OutputStreamWriter(System.out);
    }

    /**
     * Sets a new output, where encoded or decoded texts will be written.
     * @param outputStream new stream to store output.
     */
    public void setOutput(OutputStream outputStream) {
        output = new OutputStreamWriter(outputStream);
    }

    /**
     * Encodes text in <b>reader</b> into Morse code.
     * @param reader Reader to read text from
     * @throws IOException thrown when reader is null or fails
     */
    public void encodeFile(Reader reader) throws IOException {
        if (reader == null) throw new IOException("Specified reader was null");

        Map<Character, CharacterFrequency> mapStats = new HashMap<>();
        String encoded = "";

        while (reader.ready()) {
            char ch = getCasedCharacter(reader.read());
            if (ch != ' ') {
                encoded = encodeMap.getOrDefault(ch, UNKNOWN_CHAR + "") + " ";
                if (!encoded.startsWith(UNKNOWN_CHAR + "")) {
                    if (mapStats.containsKey(ch)) mapStats.get(ch).increaseFrequency();
                    else mapStats.put(ch, new CharacterFrequency(ch));
                }
            }
            else encoded = " / ";
            output.write(encoded);
        }

        output.flush();
        stats = new TreeSet<>(mapStats.values());
    }

    /**
     * Decodes text in <b>reader</b> from Morse code.
     * @param reader Reader to read text from
     * @throws IOException thrown when reader is null or fails
     */
    public void decodeFile(Reader reader) throws IOException {
        if (reader == null) throw new IOException("Specified reader was null");

        Map<Character, CharacterFrequency> mapStats = new HashMap<>();
        StringBuilder letter = new StringBuilder();
        char decoded;

        while (reader.ready()) {
            char ch = getCasedCharacter(reader.read());
            if (ch == ' ') {
                if (!letter.isEmpty()) {
                    decoded = getCasedCharacter(decodeMap.getOrDefault(letter.toString(), UNKNOWN_CHAR));
                    if (decoded != UNKNOWN_CHAR) {
                        if (mapStats.containsKey(decoded)) mapStats.get(decoded).increaseFrequency();
                        else mapStats.put(decoded, new CharacterFrequency(decoded));
                    }
                    letter.setLength(0);
                }
                else continue;
            }
            else if (ch == '/') decoded = ' ';
            else {
                letter.append(ch);
                continue;
            }
            output.write(decoded);
        }

        if (!letter.isEmpty()) {
            decoded = getCasedCharacter(decodeMap.getOrDefault(letter.toString(), UNKNOWN_CHAR));
            if (decoded != UNKNOWN_CHAR) {
                if (mapStats.containsKey(decoded)) mapStats.get(decoded).increaseFrequency();
                else mapStats.put(decoded, new CharacterFrequency(decoded));
            }
            output.write(decoded);
        }

        output.flush();
        stats = new TreeSet<>(mapStats.values());
    }

    /**
     * Writes characters frequencies inside <b>writer</b>.
     * Stats are generated after using <b>encodeFile</b> or <b>decodeFile</b>.
     * @param writer Writer where stats will be stored.
     * @throws IOException thrown when writer is null or fails
     */
    public void generateStats(Writer writer) throws IOException {
        if (writer == null) throw new IOException("Specified writer was null");

        if (stats != null) {
            StringBuilder sb = new StringBuilder();
            for (var ch : stats) {
                sb.insert(0, ch.getChar() + " = " + ch.getFrequency() + System.lineSeparator());
            }
            writer.write(sb.toString());
        }
    }

    private char getCasedCharacter(int code) {
        return switch (charCase) {
            case LOWER -> (char) Character.toLowerCase(code);
            case UPPER -> (char) Character.toUpperCase(code);
        };
    }
}
