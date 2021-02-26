package ru.nsu.spirin.morsecoder.coder;

import java.io.IOException;

public interface Coder {
    static String[] splitNameAndExtension(String fileName) {
        if (fileName == null) return null;
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) return new String[] { fileName, "" };
        return new String[] { fileName.substring(0, pos), fileName.substring(pos) };
    }

    void translateFile(String fileName) throws IOException;
}
