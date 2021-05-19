package ru.nsu.spirin.chess.controller;

import ru.nsu.spirin.chess.factory.CommandFactory;
import ru.nsu.spirin.chess.factory.Factory;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Controller {
    private final Factory<Command> commandFactory;

    public Controller(Scene scene) throws IOException {
        this.commandFactory = new CommandFactory(scene);
    }

    public void execute(String command) {
        if (command == null || command.trim().equals("")) return;
        Pair<String, String[]> split = disassembleCommand(command);
        Command cmd = commandFactory.get(split.getFirst().toUpperCase());
        CommandStatus status = cmd.execute(split.getSecond());
        System.out.print(status.getMessage());
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
        if (curToken.length() != 0) list.add(curToken.toString());
        return new Pair<>(list.get(0), list.stream().skip(1).toArray(String[]::new));
    }
}
