package ru.nsu.spirin.chess.controller.commands;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.controller.Command;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.player.ai.MiniMax;
import ru.nsu.spirin.chess.player.ai.MoveStrategy;
import ru.nsu.spirin.chess.properties.SettingsFile;
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

        try {
            SettingsFile.saveSetting("LAST_USED_NAME", args[1]);
        }
        catch (Exception ignored) {}

        getScene().setBoard(Board.createStandardBoard(!isWhitePlayer, isWhitePlayer, whitePlayerName, blackPlayerName));
        getScene().setPlayer(args[1], isWhitePlayer ? Alliance.WHITE : Alliance.BLACK);
        getScene().setSceneState(SceneState.BOARD_MENU);
        new Thread(() -> {
            while (true) {
                boolean shouldStop = false;
                try {
                    while (!(getScene().getBoard().getCurrentPlayer().isInCheckMate() || getScene().getBoard().getCurrentPlayer().isInStaleMate() || getScene().getBoard().getCurrentPlayer().isResigned())) {
                        if (getScene().getBoard().getCurrentPlayer().getAlliance() != getScene().getPlayerTeam()) {
                            MoveStrategy miniMax = new MiniMax(4);
                            Move aiMove = miniMax.execute(getScene().getBoard());
                            if (aiMove != null) {
                                MoveTransition transition = getScene().getBoard().getCurrentPlayer().makeMove(aiMove);
                                if (transition.getMoveStatus().isDone()) {
                                    getScene().setBoard(transition.getTransitionBoard());
                                    getScene().getMoveLog().addMove(aiMove);
                                }
                            }
                        }
                    }
                    if (getScene().getBoard().getCurrentPlayer().isInCheckMate() || getScene().getBoard().getCurrentPlayer().isInStaleMate() || getScene().getBoard().getCurrentPlayer().isResigned() ||
                        getScene().getBoard().getCurrentPlayer().getOpponent().isInCheckMate() || getScene().getBoard().getCurrentPlayer().getOpponent().isInStaleMate() || getScene().getBoard().getCurrentPlayer().getOpponent().isResigned()){
                        shouldStop = true;
                    }
                }
                catch (Exception ignored) {
                }
                if (shouldStop || getScene().getBoard() == null) break;
            }
        }).start();
        return true;
    }
}
