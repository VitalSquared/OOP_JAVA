package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public final class StartCommand extends Command {
    public StartCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        boolean isWhitePlayer= args[0].equalsIgnoreCase("white");
        String whitePlayerName = isWhitePlayer ? args[1] : "AI";
        String blackPlayerName = isWhitePlayer ? "AI" : args[1];

        getScene().setBoard(Board.createStandardBoard(!isWhitePlayer, isWhitePlayer, whitePlayerName, blackPlayerName));
        getScene().setSceneState(SceneState.BOARD_MENU);
        return true;
    }
}
