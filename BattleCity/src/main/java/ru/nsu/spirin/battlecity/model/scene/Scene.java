package ru.nsu.spirin.battlecity.model.scene;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private final List<Entity> entityList = new ArrayList<>();

    public List<Entity> getEntityList() {
        return entityList;
    }

    public abstract void update();
}
