package ru.nsu.spirin.morsecoder.commands;

import ru.nsu.spirin.morsecoder.coder.Alphabet;

import java.io.IOException;

public class AlphabetCommand implements Command {
    Alphabet alphabet;

    public AlphabetCommand(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length != 2) return false;
        try {
            alphabet.setNewAlphabet(args[1]);
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}
