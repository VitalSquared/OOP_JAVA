package ru.nsu.spirin.LogoWorld.drawing;

import ru.nsu.spirin.LogoWorld.logic.Executor;
import ru.nsu.spirin.LogoWorld.logic.Game;
import ru.nsu.spirin.LogoWorld.math.Pair;

import java.util.Scanner;

public class ConsoleView {

    private final int TEXTURE_SIZE = 2;

    private final Game game;

    private final Texture backgroundTexture;
    private final Texture executorTexture;
    private final Texture drawingTexture;

    public ConsoleView() {
        game = new Game();
        backgroundTexture = new Texture("    ", "");
        executorTexture = new Texture("Exec", "");
        drawingTexture = new Texture("####", "");
    }

    public void run() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String[] command;
        do {
            command = scanner.nextLine().split(" +");
            if (command.length == 0) continue;
            if (command[0].equals("EXIT")) break;
            if (game.parseCommand(command)) {
                while (game.step()) {
                    renderMap();
                    Thread.sleep(300);
                }
            }
        } while (true);
    }

    private void renderMap() {
        clearScreen();
        if (game.getField().getWidth() != 0 && game.getField().getHeight() != 0) {
            int width = 100;
            int height = 10;

            Executor executor = game.getExecutor();

            int map_width = (width) / (TEXTURE_SIZE + 1);
            int map_height = (height) / (TEXTURE_SIZE + 1);

            Pair executorCoords = executor.getPosition();

            int top_left_r = executorCoords.getX() - map_height / 2;
            int top_left_c = executorCoords.getY() - map_width / 2;

            String[][] buffer = new String[map_height * TEXTURE_SIZE][map_width * TEXTURE_SIZE];

            for (int r = 0; r < map_height; r++) {
                for (int c = 0; c < map_width; c++) {
                    int coords_r = r + top_left_r;
                    int coords_c = c + top_left_c;
                    boolean isDrawn = game.getField().isDrawn(coords_r, coords_c);
                    putTextureInBuffer(buffer, isDrawn ? drawingTexture : backgroundTexture, new Pair(TEXTURE_SIZE * r, TEXTURE_SIZE * c));
                }
            }

            int pos_r = executorCoords.getX();
            int pos_c = executorCoords.getY();
            putTextureInBuffer(buffer, executorTexture, new Pair(TEXTURE_SIZE * (pos_r - top_left_r), TEXTURE_SIZE * (pos_c - top_left_c)));

            StringBuilder output = new StringBuilder();
            int i = 0;
            for (var row : buffer) {
                int j = 0;
                //output.append(" ".repeat(padding_lft));
                for (var col : row) {
                    output.append(col).append(j == TEXTURE_SIZE - 1 ? "|" : "");
                    j++;
                    if (j >= TEXTURE_SIZE) j = 0;
                }
                output.append(System.lineSeparator());
                if (i == TEXTURE_SIZE - 1) {
                    //output.append(" ".repeat(padding_lft));
                    output.append("-".repeat(row.length * (TEXTURE_SIZE + 1) / TEXTURE_SIZE));
                    output.append(System.lineSeparator());
                }
                i++;
                if (i >= TEXTURE_SIZE) i = 0;
            }

            System.out.println(output.toString());
        }
        else {
            System.out.println("You have to init the map first. Use INIT <width> <height> <x> <y>");
        }
    }

    private void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    private void putTextureInBuffer(String[][] buffer, Texture texture, Pair top_left) {
        for (int tr = 0; tr < texture.getSize(); tr++) {
            for (int tc = 0; tc < texture.getSize(); tc++) {
                int r = top_left.getX() + tr;
                int c = top_left.getY() + tc;
                buffer[r][c] = texture.getPixel(tr, tc);
            }
        }
    }
}
