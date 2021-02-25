package ru.nsu.spirin.logoworld.drawing;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.exceptions.InvalidTextureSizeException;
import ru.nsu.spirin.logoworld.exceptions.RenderException;
import ru.nsu.spirin.logoworld.logic.Interpreter;
import ru.nsu.spirin.logoworld.logic.World;
import ru.nsu.spirin.logoworld.math.Pair;

import java.util.Scanner;

public class ConsoleView implements GraphicsView{

    private static final Logger logger = Logger.getLogger(ConsoleView.class);

    private final int TEXTURE_SIZE = 1;

    private final Texture backgroundTexture;
    private final Texture executorTexture;
    private final Texture drawingTexture;

    private final Scanner scanner;

    public ConsoleView() throws InvalidTextureSizeException {
        logger.debug("Console View initialization.");
        scanner = new Scanner(System.in);
        backgroundTexture = new Texture(" ", "");
        executorTexture = new Texture("@", "");
        drawingTexture = new Texture("#", "");
    }

    public void writeInformation(String info) {
        System.out.println(info);
    }

    public boolean getContinuationSignal() {
        String answer = scanner.next();
        return answer.equalsIgnoreCase("Y");
    }

    public void render(World world) throws RenderException {
        clearScreen();

        if (world.isValid()) {
            Pair fieldSize = world.getFieldSize();

            int width = Math.min(200, fieldSize.getFirst() * (TEXTURE_SIZE + 1));
            int height = Math.min(50, fieldSize.getSecond() * (TEXTURE_SIZE + 1));

            int padding_top = TEXTURE_SIZE + 1;
            int padding_btm = 2 * (TEXTURE_SIZE + 1);
            int padding_lft = TEXTURE_SIZE + 1;
            int padding_rgt = 2 * (TEXTURE_SIZE + 1);

            int map_width = (width - (padding_lft + padding_rgt)) / (TEXTURE_SIZE + 1);
            int map_height = (height - (padding_top + padding_btm)) / (TEXTURE_SIZE + 1);

            Pair executorCoords = world.getTurtlePosition();

            int top_left_r = executorCoords.getFirst() - map_height / 2;
            int top_left_c = executorCoords.getSecond() - map_width / 2;

            String[][] buffer = new String[map_height * TEXTURE_SIZE][map_width * TEXTURE_SIZE];

            for (int r = 0; r < map_height; r++) {
                for (int c = 0; c < map_width; c++) {
                    int coords_r = r + top_left_r;
                    int coords_c = c + top_left_c;
                    boolean isDrawn = world.isCellDrawn(coords_r, coords_c);
                    putTextureInBuffer(buffer, isDrawn ? drawingTexture : backgroundTexture, new Pair(TEXTURE_SIZE * r, TEXTURE_SIZE * c));
                }
            }

            int pos_r = executorCoords.getFirst();
            int pos_c = executorCoords.getSecond();
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

    private void clearScreen() throws RenderException {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (Exception e) {
            throw new RenderException(e.getLocalizedMessage());
        }
    }

    private void putTextureInBuffer(String[][] buffer, Texture texture, Pair top_left) {
        for (int tr = 0; tr < texture.getSize(); tr++) {
            for (int tc = 0; tc < texture.getSize(); tc++) {
                int r = top_left.getFirst() + tr;
                int c = top_left.getSecond() + tc;
                buffer[r][c] = texture.getPixel(tr, tc);
            }
        }
    }
}
