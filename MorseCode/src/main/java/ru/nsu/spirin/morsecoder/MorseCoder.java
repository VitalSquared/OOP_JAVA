package ru.nsu.spirin.morsecoder;

import ru.nsu.spirin.morsecoder.character.CharacterCase;
import ru.nsu.spirin.morsecoder.coder.Alphabet;
import ru.nsu.spirin.morsecoder.coder.Coder;
import ru.nsu.spirin.morsecoder.coder.Decoder;
import ru.nsu.spirin.morsecoder.coder.Encoder;

import java.io.IOException;
import java.util.Scanner;

public class MorseCoder {
    public void run() {
        CharacterCase charCase = CharacterCase.UPPER;
        Alphabet alphabet;
        try {
            alphabet = new Alphabet();
            alphabet.setCharacterCase(charCase);
        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            alphabet = null;
        }

        Coder encoder = new Encoder(alphabet);
        Coder decoder = new Decoder(alphabet);
        Scanner scanner = new Scanner(System.in);
        String cmd;

        printHelp();

        while (true) {
            cmd = scanner.nextLine();
            if (cmd.equals("exit")) break;

            String[] args = cmd.split(" +");
            if (args.length != 2) {
                System.out.println("Wrong number of arguments or invalid  command");
            }
            if (alphabet == null) {
                if (!args[0].equals("alphabet")) {
                    System.out.println("Before you can code/decode, you need to specify an alphabet file!");
                    continue;
                }
            }
            try {
                switch (args[0]) {
                    case "code" -> {
                        encoder.setAlphabet(alphabet);
                        encoder.translateFile(args[1]);
                    }
                    case "decode" -> {
                        decoder.setAlphabet(alphabet);
                        decoder.translateFile(args[1]);
                    }
                    case "alphabet" -> {
                        alphabet = new Alphabet(args[1]);
                        alphabet.setCharacterCase(charCase);
                    }
                    case "case" -> {
                        if (args[1].equals("UPPER") || args[1].equals("LOWER")) {
                            charCase = args[1].equals("UPPER") ? CharacterCase.UPPER : CharacterCase.LOWER;
                            alphabet.setCharacterCase(charCase);
                        }
                        else System.out.println("Invalid case value");
                    }
                    default -> System.out.println("Invalid command");
                }
            }
            catch (IOException e) {
                System.err.println("Error occurred while working: " + e.getLocalizedMessage());
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                List of commands:\s
                \tcode <filename> - translate <filename> to Morse code
                \tdecode <filename> - translate <filename> from Morse code
                \talphabet <alphabetFileName> - set new alphabet
                \tcase [UPPER | LOWER] - set preferred output character case
                \texit
                """);
    }
}
