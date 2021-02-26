package ru.nsu.spirin.morsecoder;

import ru.nsu.spirin.morsecoder.coder.Alphabet;
import ru.nsu.spirin.morsecoder.coder.Coder;
import ru.nsu.spirin.morsecoder.coder.Decoder;
import ru.nsu.spirin.morsecoder.coder.Encoder;
import ru.nsu.spirin.morsecoder.commands.AlphabetCommand;
import ru.nsu.spirin.morsecoder.commands.CaseCommand;
import ru.nsu.spirin.morsecoder.commands.Command;
import ru.nsu.spirin.morsecoder.commands.DecodeCommand;
import ru.nsu.spirin.morsecoder.commands.EncodeCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MorseCoder {

    private final Map<String, Command> commandMap;
    private final Alphabet alphabet = new Alphabet();
    private final Coder encoder = new Encoder(alphabet);;
    private final Coder decoder = new Decoder(alphabet);;

    public MorseCoder() throws IOException {
        commandMap = new HashMap<>();
        commandMap.put("code", new EncodeCommand(encoder));
        commandMap.put("decode", new DecodeCommand(decoder));
        commandMap.put("alphabet", new AlphabetCommand(alphabet));
        commandMap.put("case", new CaseCommand(alphabet));
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String cmd;

        printHelp();

        while (true) {
            cmd = scanner.nextLine();
            if (cmd.equals("exit")) break;
            String[] args = cmd.split(" +");
            if (commandMap.containsKey(args[0].toLowerCase())) {
                if (!commandMap.get(args[0].toLowerCase()).execute(args)) {
                    System.out.println("Invalid args");
                }
            }
            else {
                System.out.println("Invalid command");
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
