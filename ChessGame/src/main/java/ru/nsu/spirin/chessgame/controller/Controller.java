package ru.nsu.spirin.chessgame.controller;

import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Controller {
    private final Scene          scene;
    private final CommandFactory commandFactory;

    public Controller(final Scene scene) throws IOException {
        this.scene = scene;
        this.commandFactory = new CommandFactory(scene);
    }

    public boolean execute(final String command, boolean privileged) {
        if (command == null || command.equals("")) {
            return true;
        }

        final Pair<String, String[]> split = disassembleCommand(command);
        Command cmd = commandFactory.getCommand(split.getFirst().toUpperCase());
        return cmd.execute(split.getSecond(), privileged);
    }

    private Pair<String, String[]> disassembleCommand(String command) {
        List<String> list = new ArrayList<>();

        char[] characters = command.toCharArray();
        StringBuilder curToken = new StringBuilder();

        for (char ch : characters) {
            if (ch == ' ') {
                if (curToken.length() != 0) {
                    list.add(curToken.toString());
                    curToken.setLength(0);
                }
                continue;
            }
            curToken.append(ch);
        }

        if (curToken.length() != 0) {
            list.add(curToken.toString());
        }
        curToken.setLength(0);

        return new Pair<>(list.get(0), list.stream().skip(1).toArray(String[]::new));
    }
}
