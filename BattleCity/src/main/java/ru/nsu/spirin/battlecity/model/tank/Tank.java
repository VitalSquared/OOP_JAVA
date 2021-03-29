package ru.nsu.spirin.battlecity.model.tank;

import ru.nsu.spirin.battlecity.model.Entity;

public abstract class Tank extends Entity {
    private boolean shootNotificationPending = false;

    public abstract void shoot();

    protected void setShootNotificationPending(boolean value) {
        shootNotificationPending = value;
    }

    public boolean getShootNotificationPending() {
        return shootNotificationPending;
    }
}
