package ru.nsu.spirin.morsecoder;

import ru.nsu.spirin.morsecoder.character.CharacterFrequency;
import ru.nsu.spirin.morsecoder.character.CharacterCase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class MorseCoder {
    private char unknownCharacter = '#';
    private char lettersSeparator = ' ';
    private char wordsSeparator = '/';
    private CharacterCase charCase = CharacterCase.LOWER;

    private final Map<Character, String> encodeMap;
    private final Map<String, Character> decodeMap;
    private Set<CharacterFrequency> stats = null;

    private Writer output;

    /**
     * Creates a MorseCoder, which uses <b>default alphabet</b>
     * @throws IOException if default alphabet file is not found or invalid
     * @see ru.nsu.spirin.morsecoder.MorseCoder#MorseCoder(String)
     */
    public MorseCoder() throws IOException {
        this(ClassLoader.getSystemResourceAsStream("default_alphabet.properties"));
    }

    /**
     * Creates a MorseCoder, which uses alphabet specified in <b>alphabetFileName</b>
     * @param alphabetFileName name of the file where Morse alphabet is stored
     * @throws IOException if file is not found or invalid
     * @see ru.nsu.spirin.morsecoder.MorseCoder#MorseCoder()
     */
    public MorseCoder(String alphabetFileName) throws IOException {
        this(new FileInputStream(alphabetFileName));
    }

    private MorseCoder(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream);
        Properties properties = new Properties();

        properties.load(reader);
        reader.close();

        encodeMap = new TreeMap<>(CharacterCase.CASE_INSENSITIVE_ORDER);
        decodeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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
     * Sets a new output, where encoded or decoded texts will be written
     * @param outputStream new stream to store output.
     */
    public void setOutput(OutputStream outputStream) {
        output = new OutputStreamWriter(outputStream);
    }

    /**
     * Sets a new case of characters in output
     * @param newCase new case of characters in output
     */
    public void setOutputCharacterCase(CharacterCase newCase) {
        charCase = newCase;
    }

    /**
     * Sets new unknown character.
     * Unknown characters will be written when they are not specified in alphabet
     * @param unknownCharacter new unknown character
     */
    public void setUnknownCharacter(char unknownCharacter) {
        this.unknownCharacter = unknownCharacter;
    }

    /**
     * Sets new letters separator.
     * This character will split letters in Morse code
     * @param separator new letters separator
     */
    public void setLettersSeparator(char separator) {
        lettersSeparator = separator;
    }

    /**
     * Sets new words separator.
     * This character will split words in Morse code
     * @param separator new words separator
     */
    public void setWordsSeparator(char separator) {
        wordsSeparator = separator;
    }

    /**
     * Encodes text in {@code Reader} into Morse code
     * @param reader {@code Reader} to read text from
     * @throws IOException if {@code Reader} is null or fails
     * @see ru.nsu.spirin.morsecoder.MorseCoder#decode(Reader)
     */
    public void encode(Reader reader) throws IOException {
        if (reader == null) throw new IOException("Specified reader was null");

        Map<Character, CharacterFrequency> mapStats = new HashMap<>();
        String encoded;
        Character prev = null;

        while (reader.ready()) {
            char ch = (char) reader.read();
            if (!Character.isWhitespace(ch)) {
                encoded = encodeMap.getOrDefault(ch, unknownCharacter + "") + lettersSeparator;
                if (!encoded.startsWith(unknownCharacter + "")) {
                    if (mapStats.containsKey(ch)) mapStats.get(ch).increaseFrequency();
                    else mapStats.put(ch, new CharacterFrequency(getCasedCharacter(ch)));
                }
            }
            else {
                if (prev == null || !Character.isWhitespace(prev)) {
                    encoded = wordsSeparator + "" + lettersSeparator;
                }
                else continue;
            }
            output.write(getCasedString(encoded));
            prev = ch;
        }

        output.flush();
        stats = new TreeSet<>(mapStats.values());
    }

    /**
     * Decodes text in {@code Reader} from Morse code.
     * @param reader {@code Reader} to read text from
     * @throws IOException if {@code Reader} is null or fails
     * @see ru.nsu.spirin.morsecoder.MorseCoder#encode(Reader)
     */
    public void decode(Reader reader) throws IOException {
        if (reader == null) throw new IOException("Specified reader was null");

        Map<Character, CharacterFrequency> mapStats = new HashMap<>();
        StringBuilder letter = new StringBuilder();
        char decoded;

        while (reader.ready()) {
            char ch = (char) reader.read();
            if (ch == lettersSeparator) {
                if (!letter.isEmpty()) {
                    decoded = decodeMap.getOrDefault(letter.toString(), unknownCharacter);
                    if (decoded != unknownCharacter) {
                        if (mapStats.containsKey(decoded)) mapStats.get(decoded).increaseFrequency();
                        else mapStats.put(decoded, new CharacterFrequency(getCasedCharacter(decoded)));
                    }
                    letter.setLength(0);
                }
                else continue;
            }
            else if (ch == wordsSeparator) decoded = ' ';
            else {
                letter.append(ch);
                continue;
            }
            output.write(getCasedCharacter(decoded));
        }

        if (!letter.isEmpty()) {
            decoded = decodeMap.getOrDefault(letter.toString(), unknownCharacter);
            if (decoded != unknownCharacter) {
                if (mapStats.containsKey(decoded)) mapStats.get(decoded).increaseFrequency();
                else mapStats.put(decoded, new CharacterFrequency(getCasedCharacter(decoded)));
            }
            output.write(getCasedCharacter(decoded));
        }

        output.flush();
        stats = new TreeSet<>(mapStats.values());
    }

    /**
     * Writes characters frequencies inside {@code Writer}.
     * Stats are generated after using <b>encodeFile</b> or <b>decodeFile</b>
     * @param writer {@code Writer} where stats will be stored
     * @throws IOException if {@code Writer} is null or fails
     * @see ru.nsu.spirin.morsecoder.MorseCoder#encode(Reader)
     * @see ru.nsu.spirin.morsecoder.MorseCoder#decode(Reader)
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

    private char getCasedCharacter(char ch) {
        return switch (charCase) {
            case LOWER -> Character.toLowerCase(ch);
            case UPPER -> Character.toUpperCase(ch);
        };
    }

    private String getCasedString(String str) {
        return switch (charCase) {
            case LOWER -> str.toLowerCase();
            case UPPER -> str.toUpperCase();
        };
    }
}
