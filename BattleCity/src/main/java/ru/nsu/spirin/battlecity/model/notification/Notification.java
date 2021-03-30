package ru.nsu.spirin.battlecity.model.notification;

import ru.nsu.spirin.battlecity.math.Direction;

public class Notification {
    private final Context context;
    private final Direction direction;

    public Notification(Context context, Direction direction) {
        this.context = context;
        this.direction = direction;
    }

    public Context getContext() {
        return this.context;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
