package ru.nsu.spirin.chessgame.controller;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.move.MoveFactory;
import ru.nsu.spirin.chessgame.move.MoveTransition;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public final class Controller {

    private final Scene scene;

    public Controller(final Scene scene) {
        this.scene = scene;
    }

    public boolean execute(final String command, boolean privileged) {
        if (command == null || command.equals("")) {
            return true;
        }

        final Pair<String, String[]> split = disassembleCommand(command);
        switch (scene.getSceneState()) {
            case MAIN_MENU -> {
                switch (split.getFirst()) {
                    case "new_game" -> {
                        if (split.getSecond().length == 0) {
                            return false;
                        }
                        if (split.getSecond()[0].equals("-singleplayer") && split.getSecond().length == 5) {
                            scene.setBoard(Board.createStandardBoard(Boolean.parseBoolean(split.getSecond()[3]), Boolean.parseBoolean(split.getSecond()[4])));
                        }
                        else {
                            return false;
                        }
                    }
                    case "exit" -> {
                        scene.destroyScene();
                    }
                    default -> {
                        return false;
                    }
                }
            }
            case GAME -> {
                switch (split.getFirst()) {
                    case "move" -> {
                        if (split.getSecond().length == 2) {
                            final int sourceCoordinate = BoardUtils.getCoordinateAtPosition(split.getSecond()[0]);
                            final int destinationCoordinate = BoardUtils.getCoordinateAtPosition(split.getSecond()[1]);

                            if (sourceCoordinate == -1 || destinationCoordinate == -1) {
                                return false;
                            }

                            final Move move = MoveFactory.createMove(scene.getBoard(), sourceCoordinate, destinationCoordinate);
                            final MoveTransition transition = scene.getBoard().getCurrentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                scene.setBoard(transition.getTransitionBoard());
                                scene.getMoveLog().addMove(move);
                            }
                            else {
                                return false;
                            }
                        }
                        else {
                            return false;
                        }
                    }
                    case "ai_move" -> {
                        Move aiMove = scene.getBoard().checkAI();
                        if (aiMove != null) {
                            final MoveTransition transition = scene.getBoard().getCurrentPlayer().makeMove(aiMove);
                            if (transition.getMoveStatus().isDone()) {
                                scene.setBoard(transition.getTransitionBoard());
                                scene.getMoveLog().addMove(aiMove);
                            }
                            else {
                                return false;
                            }
                        }
                    }
                    case "surrender" -> {
                        scene.setBoard(null);
                    }
                    default -> {
                        return false;
                    }
                }
            }
        }
        return true;
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
