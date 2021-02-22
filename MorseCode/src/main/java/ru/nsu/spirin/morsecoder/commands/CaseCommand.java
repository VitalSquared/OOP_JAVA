package ru.nsu.spirin.morsecoder.commands;

import ru.nsu.spirin.morsecoder.character.CharacterCase;
import ru.nsu.spirin.morsecoder.coder.Alphabet;

public class CaseCommand implements Command {
    Alphabet alphabet;

    public CaseCommand(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length != 2 || (!args[1].equals("lower") && !args[1].equals("upper"))) {
            return false;
        }
        alphabet.setCharacterCase(args[1].equals("lower") ? CharacterCase.LOWER : CharacterCase.UPPER);
        return true;
    }
}
