package ru.nsu.spirin.battlecity.model.scene.mainmenu.selectable;

import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.scene.Entity;

public final class MenuSelectable extends Entity {
    private boolean selected = false;
    private final SelectableType type;
    private MenuSelectable next = null;
    private MenuSelectable prev = null;

    public MenuSelectable(SelectableType type, Point2D pos, Point2D size) {
        this.type = type;
        setPosition(pos);
        setSize(size);
    }

    public void onPressed() {
        if (selected) {
            createNotification(type.getNotification());
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public SelectableType getSelectableType() {
        return this.type;
    }

    public void setNext(MenuSelectable next) {
        this.next = next;
    }

    public void setPrev(MenuSelectable prev) {
        this.prev = prev;
    }

    public MenuSelectable getNext() {
        return this.next;
    }

    public MenuSelectable getPrev() {
        return this.prev;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean onCollideWith(Entity otherEntity) {
        return false;
    }
}
