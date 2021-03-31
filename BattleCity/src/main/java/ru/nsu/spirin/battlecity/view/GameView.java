package ru.nsu.spirin.battlecity.view;

import ru.nsu.spirin.battlecity.exceptions.FactoryException;
import ru.nsu.spirin.battlecity.model.scene.Scene;

public interface GameView {
    void render(Scene scene) throws FactoryException;
    void close();
}
