package ru.nsu.spirin.chessgame.view.swing;

import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.player.PlayerType;
import ru.nsu.spirin.chessgame.player.ai.MiniMax;
import ru.nsu.spirin.chessgame.player.ai.MoveStrategy;

import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;

class AIThinkTank extends SwingWorker<Move, String> {

    AIThinkTank() {

    }

    @Override
    protected Move doInBackground() throws Exception {
        /*final MoveStrategy miniMax = new MiniMax(4);

        final Move bestMove = miniMax.execute(SwingView.get().getGameBoard());

        return bestMove;*/
        return null;
    }

    @Override
    public void done() {
        /*try {
            final Move bestMove = get();
            SwingView.get().updateComputerMove(bestMove);
            SwingView.get().updateGameBoard(SwingView.get().getGameBoard().getCurrentPlayer().makeMove(bestMove).getTransitionBoard());
            SwingView.get().getMoveLog().addMove(bestMove);
            SwingView.get().getGameHistoryPanel().redo(SwingView.get().getGameBoard(), SwingView.get().getMoveLog());
            SwingView.get().getTakenPiecesPanel().redo(SwingView.get().getMoveLog());
            SwingView.get().getBoardPanel().drawBoard(SwingView.get().getGameBoard());
            SwingView.get().moveMadeUpdate(PlayerType.COMPUTER);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }
}
