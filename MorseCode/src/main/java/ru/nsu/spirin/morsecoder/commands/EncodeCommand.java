package ru.nsu.spirin.morsecoder.commands;

import ru.nsu.spirin.morsecoder.coder.Coder;

import java.io.IOException;

public class EncodeCommand implements Command {
    Coder encoder;

    public EncodeCommand(Coder encoder) {
        this.encoder = encoder;
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length != 2) return false;
        try {
            encoder.translateFile(args[1]);
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}
