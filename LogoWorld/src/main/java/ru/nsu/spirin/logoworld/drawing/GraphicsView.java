package ru.nsu.spirin.logoworld.drawing;

import ru.nsu.spirin.logoworld.exceptions.RenderException;
import ru.nsu.spirin.logoworld.logic.World;

public interface GraphicsView {
    void writeInformation(String info);
    boolean getContinuationSignal();

    void render(World world) throws RenderException;
}
