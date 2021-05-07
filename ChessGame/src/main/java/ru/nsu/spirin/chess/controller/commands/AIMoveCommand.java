package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public class AIMoveCommand extends Command {
    public AIMoveCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        if (!privileged || getScene().getSceneState() != SceneState.BOARD_MENU) {
            return false;
        }

        Move aiMove = getScene().getBoard().checkAI(4);
        if (aiMove != null) {
            final MoveTransition transition = getScene().getBoard().getCurrentPlayer().makeMove(aiMove);
            if (transition.getMoveStatus().isDone()) {
                getScene().setBoard(transition.getTransitionBoard());
                getScene().getMoveLog().addMove(aiMove);
                return true;
            }
            return false;
        }
        return true;
    }
}
