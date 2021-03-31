package ru.nsu.spirin.battlecity;

public class Main {

    public static void main(String[] args) {
        BattleCityGame game = null;
        try {
            game = new BattleCityGame();
            game.run();
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        if (game != null) {
            game.close();
        }
    }
}
