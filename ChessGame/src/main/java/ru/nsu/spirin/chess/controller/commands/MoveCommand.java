package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveFactory;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public class MoveCommand extends Command {
    public MoveCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        if (getScene().getSceneState() != SceneState.BOARD_MENU) {
            return false;
        }
        if (args.length == 2) {
            int sourceCoordinate = BoardUtils.getCoordinateAtPosition(args[0]);
            int destinationCoordinate = BoardUtils.getCoordinateAtPosition(args[1]);

            if (sourceCoordinate == -1 || destinationCoordinate == -1) return false;

            Move move = MoveFactory.createMove(getScene().getBoard(), sourceCoordinate, destinationCoordinate);
            MoveTransition transition = getScene().getBoard().getCurrentPlayer().makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                getScene().setBoard(transition.getTransitionBoard());
                getScene().getMoveLog().addMove(move);
                return true;
            }
            return false;
        }
        return false;
    }
}
