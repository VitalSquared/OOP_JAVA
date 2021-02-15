package ru.nsu.spirin.logoworld;

import ru.nsu.spirin.logoworld.drawing.ConsoleView;
import ru.nsu.spirin.logoworld.drawing.GraphicsView;
import ru.nsu.spirin.logoworld.exceptions.CommandsWorkflowException;
import ru.nsu.spirin.logoworld.exceptions.InvalidTextureSizeException;
import ru.nsu.spirin.logoworld.exceptions.RenderException;
import ru.nsu.spirin.logoworld.logic.Interpreter;
import ru.nsu.spirin.logoworld.logic.Program;
import ru.nsu.spirin.logoworld.logic.World;

import java.io.IOException;

public class LogoWorld {

    World world;
    GraphicsView graphicsView;
    Interpreter interpreter;
    Program program;

    public LogoWorld(String programFileName) throws IOException, InvalidTextureSizeException {
        program = new Program(programFileName);
        world = new World();
        graphicsView = new ConsoleView();
        interpreter = new Interpreter(world);
    }

    public void run() throws CommandsWorkflowException, RenderException, RuntimeException {
        while (!program.isFinished()) {
            if (interpreter.parseCommand(program.nextCommand())) {
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
                graphicsView.writeInformation("Invalid command. Do you want to continue executing program? (Y/N)");
                if (graphicsView.getContinuationSignal()) break;
            }
        }
        program.close();
    }
}
