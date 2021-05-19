package ru.nsu.spirin.chess.model.match;

import ru.nsu.spirin.chess.model.board.Board;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.move.MoveFactory;
import ru.nsu.spirin.chess.model.move.MoveTransition;
import ru.nsu.spirin.chess.model.move.ResignMove;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.player.ai.MiniMax;
import ru.nsu.spirin.chess.model.player.ai.MoveStrategy;
import ru.nsu.spirin.chess.model.server.ConnectionStatus;
import ru.nsu.spirin.chess.utils.ThreadPool;

public final class Local extends MatchEntity {
    public Local(String playerName, Alliance playerTeam) {
        super(playerName);
        setBoard(Board.createStandardBoard());
        setPlayerAlliance(playerTeam);

        ThreadPool.submitThread(new Thread(() -> {
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
        }));
    }

    @Override
    public String getOpponentName() {
        return "AI";
    }

    @Override
    public Alliance getOpponentAlliance() {
        return getPlayerAlliance().getOpposite();
    }

    @Override
    public boolean isOpponentReady() {
        return true;
    }

    @Override
    public boolean isPlayerReady() {
        return true;
    }

    @Override
    public void setPlayerReady(boolean playerReady) {

    }

    @Override
    public void makeMove(Move move, MoveTransition transition) {
        calculateScore(move);
        if (!(move instanceof ResignMove)) {
            getMoveLog().addMove(transition.getTransitionBoard(), move);
        }
        setBoard(transition.getTransitionBoard());
    }

    @Override
    public ConnectionStatus connected() {
        return ConnectionStatus.CONNECTED;
    }

    @Override
    public void closeConnection() {

    }
}
