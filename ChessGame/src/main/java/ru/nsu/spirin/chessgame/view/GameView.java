package ru.nsu.spirin.chessgame.view;

import ru.nsu.spirin.chessgame.scene.Scene;

public interface GameView {
    void render(Scene scene);
    void close();
}
