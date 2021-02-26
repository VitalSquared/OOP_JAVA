package ru.nsu.spirin.logoworld;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.commands.CommandError;
import ru.nsu.spirin.logoworld.drawing.ConsoleView;
import ru.nsu.spirin.logoworld.drawing.GraphicsView;
import ru.nsu.spirin.logoworld.drawing.SwingView;
import ru.nsu.spirin.logoworld.exceptions.CommandFactoryException;
import ru.nsu.spirin.logoworld.exceptions.InvalidInputException;
import ru.nsu.spirin.logoworld.exceptions.RenderException;
import ru.nsu.spirin.logoworld.logic.Interpreter;
import ru.nsu.spirin.logoworld.logic.World;

import java.io.IOException;

public class LogoWorld {

    private static final Logger logger = Logger.getLogger(LogoWorld.class);

    private final World world;
    private final GraphicsView graphicsView;
    private final Interpreter interpreter;

    /**
     * Create Logo World instance.
     *
     * @param programFileName Program to run. Use <b>null</b> if you want to type commands manually.
     * @param useSwing        Render in swing window, if true. Otherwise render in console.
     * @throws IOException     If program file, commands-properties file are missing
     * @throws RenderException If console uses wrong sizes of textures
     */
    public LogoWorld(String programFileName, boolean useSwing) throws IOException, RenderException {
        logger.debug("Logo World initialization...");

        world = new World();
        interpreter = new Interpreter(programFileName, world);
        if (useSwing) {
            graphicsView = new SwingView();
        }
        else {
            graphicsView = new ConsoleView();
        }
    }

    /**
     * Run Logo World
     *
     * @throws CommandFactoryException If command factory fails
     * @throws RenderException         If rendering fails
     * @throws RuntimeException        Other related issues
     */
    public void run() throws CommandFactoryException, RenderException, RuntimeException {
        try {
            while (!interpreter.isFinished()) {
                if (interpreter.parseNextCommand()) {
                    while (interpreter.step()) {
                        graphicsView.render(world);
                        try {
                            Thread.sleep(300);
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e.getLocalizedMessage());
                        }
                    }
                }
                else {
                    String error = CommandError.getError();
                    logger.debug(error);
                    graphicsView.writeInformation(error);
                    if (interpreter.shouldAskForContinuation() && !graphicsView.getContinuationSignal()) {
                        break;
                    }
                }
            }
        }
        catch (InvalidInputException e) {
            logger.debug("Invalid input. Shutting down...");
            graphicsView.writeInformation("Invalid input. Shutting down...");
        }
    }
}