package ru.nsu.spirin.chessgame.view.swing;

import java.util.Observable;
import java.util.Observer;

class TableGameAIWatcher implements Observer {

    @Override
    public void update(final Observable o, final Object arg) {
        /*if (getGameBoard().getCurrentPlayer().isAI() && !getGameBoard().getCurrentPlayer().isInCheckMate() && !getGameBoard().getCurrentPlayer().isInStaleMate()) {
            final AIThinkTank thinkTank = new AIThinkTank();
            thinkTank.execute();
        }

        if (getGameBoard().getCurrentPlayer().isInCheckMate()) {
            System.out.println("Game over," + SwingView.get().getGameBoard().getCurrentPlayer() + " is in checkmate");
        }
        if (getGameBoard().getCurrentPlayer().isInStaleMate()) {
            System.out.println("Game over," + SwingView.get().getGameBoard().getCurrentPlayer() + " is in stalemate");
        }*/
    }
}
