package ru.nsu.spirin.morsecoder.coder;

import ru.nsu.spirin.morsecoder.character.CharacterFrequency;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public interface Coder {
    Set<CharacterFrequency> stats = new HashSet<>();

    static String[] splitNameAndExtension(String fileName) {
        if (fileName == null) return null;
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) return new String[] { fileName, "" };
        return new String[] { fileName.substring(0, pos), fileName.substring(pos) };
    }

    static void addStat(Character ch) {
        stats.add(new CharacterFrequency(ch));
    }

    static void clearStats() {
        stats.clear();
    }

    static void writeStats(Alphabet alphabet, Writer statsWriter) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (var ch : stats) {
            sb.append(ch.getChar()).append(" = ").append(ch.getFrequency()).append(System.lineSeparator());
        }
        statsWriter.write(alphabet.getCasedString(sb.toString()));
        statsWriter.flush();
    }

    void setAlphabet(Alphabet newAlphabet);
    void translateFile(String fileName) throws IOException;
}
