package ru.nsu.spirin.chess;

import ru.nsu.spirin.chess.game.ChessGame;
import ru.nsu.spirin.chess.game.GameEntity;
import ru.nsu.spirin.chess.game.ServerGame;
import ru.nsu.spirin.chess.utils.ThreadPool;

public final class Main {
    public static void main(String[] args) {
        boolean isServer = false;
        boolean isSwing = true;
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "--server" -> isServer = true;
                case "--console" -> isSwing = false;
            }
        }

        GameEntity chessGame = null;
        try {
            if (isServer) chessGame = new ServerGame();
            else chessGame = new ChessGame(isSwing);
            chessGame.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (chessGame != null) chessGame.close();
        }

        ThreadPool.shutdown();
    }
}
