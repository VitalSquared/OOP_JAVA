package ru.nsu.spirin.chessgame;

public final class Main {
    public static void main(String[] args) {
        ChessGame chessGame = null;
        try {
            chessGame = new ChessGame();
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
