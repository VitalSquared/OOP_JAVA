package ru.nsu.spirin.morsecoder.coder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Encoder implements Coder {
    Alphabet alphabet;

    public Encoder(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void setAlphabet(Alphabet newAlphabet) {
        this.alphabet = newAlphabet;
    }

    @Override
    public void translateFile(String fileName) {
        String[] file = Coder.splitNameAndExtension(fileName);
        String outputFileName = file[0] + "_output" + file[1];
        String statsFileName = file[0] + "_stats" + file[1];

        BufferedReader input = null;
        Writer output = null;
        Writer stats = null;

        Coder.clearStats();

        try {
            input = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            while (input.ready()) {
                String line = input.readLine();
                for (var word : line.split("\\s+")) {
                    for (var letter : word.toCharArray()) {
                        if (Character.isLetter(letter)) {
                            Coder.addStat(letter);
                        }
                        sb.append((alphabet.getMorseCodeFromCharacter(letter))).append(" ");
                    }
                    sb.append(" / ");
                }
            }

            output = new FileWriter(outputFileName);
            output.write(alphabet.getCasedString(sb.toString()));
            output.flush();
            System.out.println("Done translating.");

            stats = new FileWriter(statsFileName);
            Coder.writeStats(alphabet, stats);
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
