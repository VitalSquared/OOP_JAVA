package ru.nsu.spirin.morsecoder.coder;

import ru.nsu.spirin.morsecoder.character.CharacterFrequency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public final class Encoder implements Coder {
    private final Alphabet alphabet;

    public Encoder(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void translateFile(String fileName) {
        String[] file = Coder.splitNameAndExtension(fileName);
        String outputFileName = file[0] + "_output" + file[1];
        String statsFileName = file[0] + "_stats" + file[1];

        BufferedReader input = null;
        Writer output = null;
        Writer stats = null;

        Set<CharacterFrequency> statsSet = new HashSet<>();

        try {
            input = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            while (input.ready()) {
                String line = input.readLine();
                for (var word : line.split("\\s+")) {
                    for (var letter : word.toCharArray()) {
                        if (Character.isLetter(letter)) {
                            statsSet.add(new CharacterFrequency(letter));
                        }
                        sb.append((alphabet.getMorseCodeFromCharacter(letter))).append(" ");
                    }
                    sb.append("/ ");
                }
            }
            sb.setLength(sb.length() - 2);
            output = new FileWriter(outputFileName);
            output.write(alphabet.getCasedString(sb.toString()));
            output.flush();
            System.out.println("Done translating.");

            sb.setLength(0);
            stats = new FileWriter(statsFileName);
            for (var ch : statsSet) {
                sb.append(ch.getChar()).append(" = ").append(ch.getFrequency()).append(System.lineSeparator());
            }
            stats.write(alphabet.getCasedString(sb.toString()));
            stats.flush();
            System.out.println("Stats file generated.");
        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        finally {
            if (input != null) {
                try { input.close(); }
                catch (IOException e) { System.err.println(e.getLocalizedMessage()); }
            }
            if (output != null) {
                try { output.close(); }
                catch (IOException e) { System.err.println(e.getLocalizedMessage()); }
            }
            if (stats != null) {
                try { stats.close(); }
                catch (IOException e) { System.err.println(e.getLocalizedMessage()); }
            }
        }
    }
}
