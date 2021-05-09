package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveFactory;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.player.Player;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

public class MoveCommand extends Command {
    public MoveCommand(Scene scene) {
        super(scene);
    }

    @Override
    public boolean execute(String[] args, boolean privileged) {
        int length = args.length - 1;

        if (getScene().getSceneState() != SceneState.BOARD_MENU) {
            return false;
        }

        Alliance alliance;
        if (args[length].equalsIgnoreCase("WHITE")) {
            alliance = Alliance.WHITE;
        }
        else if (args[length].equalsIgnoreCase("BLACK")) {
            alliance = Alliance.BLACK;
        }
        else {
            return false;
        }

        Player alliancePlayer = alliance.choosePlayer(getScene().getActiveGame().getBoard().getWhitePlayer(), getScene().getActiveGame().getBoard().getBlackPlayer());

        if (length == 2) {
            int sourceCoordinate = BoardUtils.getCoordinateAtPosition(args[0]);
            int destinationCoordinate = BoardUtils.getCoordinateAtPosition(args[1]);

            if (sourceCoordinate == -1 || destinationCoordinate == -1) return false;

            Move move = MoveFactory.createMove(getScene().getActiveGame().getBoard(), sourceCoordinate, destinationCoordinate);
            MoveTransition transition = alliancePlayer.makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                getScene().getActiveGame().makeMove(move, transition);
                return true;
            }
            return false;
        }
        else if (length == 1 && args[0].equals("resign")) {
            Move move = MoveFactory.createResignMove(getScene().getActiveGame().getBoard(), alliance);
            MoveTransition transition = alliancePlayer.makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                getScene().getActiveGame().makeMove(move, transition);
                return true;
            }
            return false;
        }
        return false;
    }


}
