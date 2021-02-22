package ru.nsu.spirin.morsecoder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            MorseCoder morseCoder = new MorseCoder();
            morseCoder.run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
