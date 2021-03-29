package ru.nsu.spirin.battlecity;

import ru.nsu.spirin.battlecity.controller.TankController;
import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.model.World;
import ru.nsu.spirin.battlecity.model.tank.PlayerTank;
import ru.nsu.spirin.battlecity.model.tank.Tank;
import ru.nsu.spirin.battlecity.view.GameView;
import ru.nsu.spirin.battlecity.view.SwingView;

import java.io.IOException;

public class BattleCityGame {

    GameView gameView;
    World world;
    Tank playerTank;
    TankController tankController;

    public BattleCityGame() throws IOException, InvalidBattleGridException {
        playerTank = new PlayerTank();
        tankController = new TankController(playerTank);
        gameView = new SwingView(tankController);
        world = new World("maps/map1.txt", playerTank);
    }

    public void run() {
        while(true) {
            gameView.render(world);
            try {
                Thread.sleep(1000/30);
            }
            catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            world.update();
        }
    }
}
