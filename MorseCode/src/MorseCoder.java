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
    private enum CharCase { LOWER, UPPER }

    private HashMap<Character, String> encodeMap = null;
    private HashMap<String, Character> decodeMap = null;
    private Set<CharFrequency> stats = null;
    private Writer output = null;
    private final CharCase charCase = CharCase.LOWER;


    public MorseCoder(String alphabetFileName) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(alphabetFileName));
        Properties properties = new Properties();

        properties.load(reader);
        reader.close();

        encodeMap = new HashMap<>();
        decodeMap = new HashMap<>();

        for (String text : properties.stringPropertyNames()) {
            String morseCode = properties.getProperty(text);
            encodeMap.put(getCasedChar(text.charAt(0)), morseCode);
            decodeMap.put(morseCode, getCasedChar(text.charAt(0)));
        }

        output = new OutputStreamWriter(System.out);
    }

    public void setOutput(OutputStream outputStream) {
        output = new OutputStreamWriter(outputStream);
    }

    public void encodeFile(Reader reader) throws IOException {
        Map<Character, CharFrequency> mapStats = new HashMap<>();
        while (reader.ready()) {
            char ch = getCasedChar(reader.read());
            String encoded;
            if (ch != ' ') {
                encoded = encodeMap.getOrDefault(ch, "#") + " ";
                if (!encoded.startsWith("#")) {
                    if (mapStats.containsKey(ch)) mapStats.get(ch).increaseFrequency();
                    else mapStats.put(ch, new CharFrequency(ch));
                }
            }
            else encoded = " / ";
            output.write(encoded);
        }
        output.flush();
        stats = new TreeSet<>(mapStats.values());
    }

    public void decodeFile(Reader reader) throws IOException {
        Map<Character, CharFrequency> mapStats = new HashMap<>();
        StringBuilder letter = new StringBuilder();
        while (reader.ready()) {
            char ch = getCasedChar(reader.read());
            String decoded = "";
            if (ch == ' ') {
                if (!letter.isEmpty()) {
                    decoded = decodeMap.getOrDefault(letter.toString(), '#').toString();
                    letter.setLength(0);
                    if (!decoded.startsWith("#")) {
                        if (mapStats.containsKey(ch))  mapStats.get(ch).increaseFrequency();
                        else mapStats.put(ch, new CharFrequency(ch));
                    }
                }
            }
            else if (ch == '/') decoded = " ";
            else letter.append(ch);
            output.write(decoded);
        }
        if (!letter.isEmpty()) output.write(decodeMap.getOrDefault(letter.toString(), '#'));
        output.flush();
        stats = new TreeSet<>(mapStats.values());
    }

    public void generateStats(Writer writer) throws IOException {
        if (stats != null) {
            StringBuilder sb = new StringBuilder();
            for (var ch : stats) {
                sb.insert(0, ch.getChar() + " = " + ch.getFrequency() + System.lineSeparator());
            }
            writer.write(sb.toString());
        }
    }

    private char getCasedChar(int code) {
        return switch (charCase) {
            case LOWER -> (char) Character.toLowerCase(code);
            case UPPER -> (char) Character.toUpperCase(code);
        };
    }
}
