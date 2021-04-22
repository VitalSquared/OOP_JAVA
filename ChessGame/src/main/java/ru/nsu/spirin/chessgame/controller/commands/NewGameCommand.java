package ru.nsu.spirin.chessgame.controller.commands;

import ru.nsu.spirin.chessgame.board.Board;
import ru.nsu.spirin.chessgame.controller.Command;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.scene.SceneState;

public class NewGameCommand extends Command {
    public NewGameCommand(final Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(final String[] args, final boolean privileged) {
        if (args.length == 0 || getScene().getSceneState() != SceneState.MAIN_MENU) {
            return false;
        }
        if (args[0].equals("-singleplayer") && args.length == 5) {
            getScene().setBoard(Board.createStandardBoard(Boolean.parseBoolean(args[3]), Boolean.parseBoolean(args[4]), args[1], args[2]));
        }
        else {
            return false;
        }
        return true;
    }
}
