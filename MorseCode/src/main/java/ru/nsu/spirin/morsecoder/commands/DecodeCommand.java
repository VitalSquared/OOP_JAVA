package ru.nsu.spirin.morsecoder.commands;

import ru.nsu.spirin.morsecoder.coder.Coder;

import java.io.IOException;

public class DecodeCommand implements Command {
    Coder decoder;

    public DecodeCommand(Coder decoder) {
        this.decoder = decoder;
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length != 2) return false;
        try {
            decoder.translateFile(args[1]);
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}
