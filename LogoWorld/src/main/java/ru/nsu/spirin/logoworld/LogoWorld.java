package ru.nsu.spirin.logoworld;

import org.apache.log4j.Logger;
import ru.nsu.spirin.logoworld.commands.CommandError;
import ru.nsu.spirin.logoworld.drawing.ConsoleView;
import ru.nsu.spirin.logoworld.drawing.GraphicsView;
import ru.nsu.spirin.logoworld.drawing.SwingView;
import ru.nsu.spirin.logoworld.exceptions.CommandsWorkflowException;
import ru.nsu.spirin.logoworld.exceptions.InvalidTextureSizeException;
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
     * @param programFileName program to run. Use <b>null</b> if you want to type commands manually.
     * @param useSwing If true, render in swing window. If false, render in console.
     * @throws IOException if program file, commands-properties file are missing
     * @throws InvalidTextureSizeException if console uses wrong sizes of textures
     */
    public LogoWorld(String programFileName, boolean useSwing) throws IOException, InvalidTextureSizeException {
        logger.debug("Logo World Initialization...");
        world = new World();
        graphicsView = useSwing ? new SwingView() : new ConsoleView();
        interpreter = new Interpreter(programFileName, world);
    }

    /**
     * Run Logo World
     * @throws CommandsWorkflowException if command factory fails
     * @throws RenderException if rendering fails
     * @throws RuntimeException other related issues
     */
    public void run() throws CommandsWorkflowException, RenderException, RuntimeException {
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
                logger.debug("Error encountered: " + error);
                graphicsView.writeInformation(error);
                if (interpreter.shouldAskForContinuation()) {
                    if (!graphicsView.getContinuationSignal()) {
                        break;
                    }
                }
            }
        }
    }
}
