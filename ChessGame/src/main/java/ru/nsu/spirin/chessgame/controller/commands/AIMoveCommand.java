package ru.nsu.spirin.chessgame.controller.commands;

import ru.nsu.spirin.chessgame.controller.Command;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.move.MoveTransition;
import ru.nsu.spirin.chessgame.scene.Scene;
import ru.nsu.spirin.chessgame.scene.SceneState;

public class AIMoveCommand extends Command {
    public AIMoveCommand(final Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(final String[] args, final boolean privileged) {
        if (!privileged || getScene().getSceneState() != SceneState.GAME) {
            return false;
        }

        Move aiMove = getScene().getBoard().checkAI(getScene().getMiniMaxDepth());
        if (aiMove != null) {
            final MoveTransition transition = getScene().getBoard().getCurrentPlayer().makeMove(aiMove);
            if (transition.getMoveStatus().isDone()) {
                getScene().setBoard(transition.getTransitionBoard());
                getScene().getMoveLog().addMove(aiMove);
            }
            else {
                return false;
            }
        }
        return true;
    }
}
