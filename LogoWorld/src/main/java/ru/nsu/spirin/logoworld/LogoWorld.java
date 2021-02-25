package ru.nsu.spirin.logoworld;

import org.apache.log4j.Logger;
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

    public LogoWorld(String programFileName, boolean useSwing) throws IOException, InvalidTextureSizeException {
        logger.debug("Logo World Initialization...");
        world = new World();
        graphicsView = useSwing ? new SwingView() : new ConsoleView();
        interpreter = new Interpreter(programFileName, world);
    }

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
                logger.debug("Invalid command.");
                graphicsView.writeInformation("Invalid command. Do you want to continue executing program? (Y/N)");
                if (graphicsView.getContinuationSignal()) break;
            }
        }
    }
}
