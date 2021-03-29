package ru.nsu.spirin.battlecity.model.map;

import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.math.Point2D;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class BattleGrid {
    private final Tile[] grid;
    private final int gridX;
    private final int gridY;

    public BattleGrid(String mapFileName) throws IOException, InvalidBattleGridException {
        Scanner scanner = new Scanner(new FileInputStream(mapFileName));

        gridX = scanner.nextInt();
        gridY = scanner.nextInt();
        grid = new Tile[gridX * gridY];
        scanner.nextLine();

        int x = 0, y = 0;
        while(scanner.hasNext()) {
            if (y >= gridY) {
                throw new InvalidBattleGridException("Invalid rows counts");
            }
            String line = scanner.nextLine();
            if (line.length() != gridX) {
                throw new InvalidBattleGridException("Invalid columns counts");
            }
            for (x = 0; x < gridX; x++) {
                char ch = line.charAt(x);
                Tile tile = Tile.charToTile(ch);
                if (tile == Tile.UNKNOWN) {
                    throw new InvalidBattleGridException("Unrecognized tile");
                }
                grid[y * gridX + x] = tile;
            }
            y++;
        }
    }

    public Tile getTileAt(int x, int y) {
        return grid[y * gridX + x];
    }

    public Point2D getGridSize() {
        return new Point2D(gridX, gridY);
    }
}
