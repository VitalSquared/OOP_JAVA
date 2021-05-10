package ru.nsu.spirin.chess.communication;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.move.MoveFactory;
import ru.nsu.spirin.chess.move.MoveTransition;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.player.ai.MiniMax;
import ru.nsu.spirin.chess.player.ai.MoveStrategy;
import ru.nsu.spirin.chess.utils.ThreadUtils;

public final class Local extends GameEntity {

    public Local(String playerName, Alliance playerTeam) {
        setBoard(Board.createStandardBoard());
        setPlayerName(playerName);
        setPlayerTeam(playerTeam);

        ThreadUtils.submitThread(
            new Thread(() -> {
                while (true) {
                    boolean shouldStop = false;
                    try {
                        while (!BoardUtils.isEndGame(getBoard())) {
                            if (getBoard().getCurrentPlayer().getAlliance() != getPlayerAlliance()) {
                                MoveStrategy miniMax = new MiniMax(4);
                                Move aiMove = miniMax.execute(getBoard());
                                if (aiMove == null) {
                                    aiMove = MoveFactory.createResignMove(getBoard(), getBoard().getCurrentPlayer().getAlliance());
                                }
                                MoveTransition transition = getBoard().getCurrentPlayer().makeMove(aiMove);
                                if (transition.getMoveStatus().isDone() && !BoardUtils.isEndGame(getBoard())) {
                                    makeMove(aiMove, transition);
                                }
                            }
                        }
                        if (BoardUtils.isEndGame(getBoard())) {
                            shouldStop = true;
                        }
                    }
                    catch (Exception ignored) {
                    }
                    if (shouldStop || getBoard() == null) break;
                }
            })
        );
    }

    @Override
    public String getOpponentName() {
        return "AI";
    }

    @Override
    public Alliance getOpponentTeam() {
        return getPlayerAlliance().getOpposite();
    }

    @Override
    public void makeMove(Move move, MoveTransition transition) {
        calculateScore(move);
        setBoard(transition.getTransitionBoard());
        getMoveLog().addMove(move);
    }
}
