package ru.nsu.spirin.chess;

import java.util.Arrays;

public final class Main {
    public static void main(String[] args) {
        boolean useSwing = !Arrays.asList(args).contains("--console");
        ChessGame chessGame = null;
        try {
            chessGame = new ChessGame(useSwing);
            chessGame.run();
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            if (chessGame != null) {
                chessGame.close();
            }
        }
    }
}
