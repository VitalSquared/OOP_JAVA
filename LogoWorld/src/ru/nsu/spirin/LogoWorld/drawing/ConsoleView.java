package ru.nsu.spirin.LogoWorld.drawing;

import ru.nsu.spirin.LogoWorld.logic.Executor;
import ru.nsu.spirin.LogoWorld.logic.Game;
import ru.nsu.spirin.LogoWorld.logic.Program;
import ru.nsu.spirin.LogoWorld.math.Pair;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleView {

    private final int TEXTURE_SIZE = 2;

    private final Game game;
    private final Program program;

    private final Texture backgroundTexture;
    private final Texture executorTexture;
    private final Texture drawingTexture;

    public ConsoleView(String programFileName) throws IOException {
        game = new Game();
        backgroundTexture = new Texture("    ", "");
        executorTexture = new Texture("Exec", "");
        drawingTexture = new Texture("####", "");
        program = new Program(programFileName);
    }

    public void run() throws InterruptedException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Scanner scanner = new Scanner(System.in);
        String[] command;
        do {
            command = program.nextCommand().trim().split(" +");
            if (command.length == 0 || command.length == 1 && command[0].equals("")) {
                continue;
            }
            if (game.parseCommand(command)) {
                while (game.step()) {
                    renderMap();
                    Thread.sleep(300);
                }
            }
            else if (program.requestContinuation()) {
                System.out.println("Invalid command. Do you want to continue executing program? (Y/N)");
                String ans = scanner.next();
                if (!ans.toUpperCase(Locale.ROOT).equals("Y")) {
                    break;
                }
            }
        } while (!program.isFinished());
        program.close();
    }

    private void renderMap() throws IOException, InterruptedException {
        clearScreen();
        Executor executor = game.getExecutor();

        if (executor.isValid()) {
            int width = Math.min(200, game.getField().getWidth() * (TEXTURE_SIZE + 1));
            int height = Math.min(50, game.getField().getHeight() * (TEXTURE_SIZE + 1));

            int padding_top = TEXTURE_SIZE + 1;
            int padding_btm = 2 * (TEXTURE_SIZE + 1);
            int padding_lft = TEXTURE_SIZE + 1;
            int padding_rgt = 2 * (TEXTURE_SIZE + 1);

            int map_width = (width - (padding_lft + padding_rgt)) / (TEXTURE_SIZE + 1);
            int map_height = (height - (padding_top + padding_btm)) / (TEXTURE_SIZE + 1);

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
            output.append(System.lineSeparator().repeat(padding_top));
            for (var row : buffer) {
                int j = 0;
                output.append(" ".repeat(padding_lft));
                for (var col : row) {
                    output.append(col).append(j == TEXTURE_SIZE - 1 ? "|" : "");
                    j++;
                    if (j >= TEXTURE_SIZE) j = 0;
                }
                output.append(System.lineSeparator());
                if (i == TEXTURE_SIZE - 1) {
                    output.append(" ".repeat(padding_lft));
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

    private void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
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
