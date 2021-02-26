package ru.nsu.spirin.logoworld.drawing;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.exceptions.RenderException;
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

    public ConsoleView() throws RenderException {
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
        System.out.println("Do you want to continue program execution? (Y/N)");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("Y");
    }

    public void render(World world) throws RenderException {
        clearScreen();

        if (world.isValid()) {
            Pair fieldSize = world.getFieldSize();

            int width = Math.min(200, fieldSize.getFirst() * (TEXTURE_SIZE + 1));
            int height = Math.min(50, fieldSize.getSecond() * (TEXTURE_SIZE + 1));

            int paddingTop = TEXTURE_SIZE + 1;
            int paddingBottom = 2 * (TEXTURE_SIZE + 1);
            int paddingLeft = TEXTURE_SIZE + 1;
            int paddingRight = 2 * (TEXTURE_SIZE + 1);

            int renderMapWidth = (width - (paddingLeft + paddingRight)) / (TEXTURE_SIZE + 1);
            int renderMapHeight = (height - (paddingTop + paddingBottom)) / (TEXTURE_SIZE + 1);

            Pair turtlePos = world.getTurtlePosition();

            int topLeftX = turtlePos.getFirst() - renderMapWidth / 2;
            int topLeftY = turtlePos.getSecond() - renderMapHeight / 2;

            String[][] buffer = new String[renderMapHeight * TEXTURE_SIZE][renderMapWidth * TEXTURE_SIZE];

            for (int y = 0; y < renderMapHeight; y++) {
                for (int x = 0; x < renderMapWidth; x++) {
                    int xPos = x + topLeftX;
                    int yPos = y + topLeftY;
                    boolean isDrawn = world.isCellDrawn(xPos, yPos);
                    putTextureInBuffer(buffer, isDrawn ? drawingTexture : backgroundTexture, new Pair(TEXTURE_SIZE * x, TEXTURE_SIZE * y));
                }
            }

            int turtleX = turtlePos.getFirst();
            int turtleY = turtlePos.getSecond();
            putTextureInBuffer(buffer, executorTexture, new Pair(TEXTURE_SIZE * (turtleX - topLeftX), TEXTURE_SIZE * (turtleY - topLeftY)));

            StringBuilder output = new StringBuilder();
            int i = 0;
            output.append(System.lineSeparator().repeat(paddingTop));
            for (var row : buffer) {
                int j = 0;
                output.append(" ".repeat(paddingLeft));
                for (var col : row) {
                    output.append(col).append(j == TEXTURE_SIZE - 1 ? "|" : "");
                    j++;
                    if (j >= TEXTURE_SIZE) j = 0;
                }
                output.append(System.lineSeparator());
                if (i == TEXTURE_SIZE - 1) {
                    output.append(" ".repeat(paddingLeft));
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

    private void putTextureInBuffer(String[][] buffer, Texture texture, Pair topLeft) {
        for (int ty = 0; ty < texture.getSize(); ty++) {
            for (int tx = 0; tx < texture.getSize(); tx++) {
                int x = topLeft.getFirst() + tx;
                int y = topLeft.getSecond() + ty;
                buffer[y][x] = texture.getPixel(tx, ty);
            }
        }
    }
}
