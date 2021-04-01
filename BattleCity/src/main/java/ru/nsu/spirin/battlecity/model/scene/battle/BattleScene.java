package ru.nsu.spirin.battlecity.model.scene.battle;

import ru.nsu.spirin.battlecity.controller.Action;
import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Context;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.scene.Entity;
import ru.nsu.spirin.battlecity.model.scene.Scene;
import ru.nsu.spirin.battlecity.model.scene.battle.tank.EnemyTank;
import ru.nsu.spirin.battlecity.model.scene.battle.tank.PlayerTank;
import ru.nsu.spirin.battlecity.model.scene.battle.tank.Tank;
import ru.nsu.spirin.battlecity.model.scene.battle.tile.Tile;
import ru.nsu.spirin.battlecity.model.scene.battle.tile.TileType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class BattleScene extends Scene {
    private final Tank playerTank;
    private Tile eagle = null;
    private int tanksKilled = 0;

    public BattleScene(String mapFileName) throws IOException, InvalidBattleGridException {
        this.playerTank = new PlayerTank(100, 100, 36, 36);

        Scanner scanner = new Scanner(new FileInputStream(mapFileName));
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");
            if (line.length != 5) {
                throw new InvalidBattleGridException("");
            }
            int posX = Integer.parseInt(line[1]);
            int posY = Integer.parseInt(line[2]);
            int sizeX = Integer.parseInt(line[3]);
            int sizeY = Integer.parseInt(line[4]);
            if (line[0].equals("brick")) {
                getEntityList().add(new Tile(TileType.BRICK, new Point2D(posX, posY), new Point2D(sizeX, sizeY)));
            }
            if (line[0].equals("border")) {
                getEntityList().add(new Tile(TileType.BORDER, new Point2D(posX, posY), new Point2D(sizeX, sizeY)));
            }
            if (line[0].equals("eagle")) {
                if (eagle != null) {
                    throw new InvalidBattleGridException("Can't have more than one eagle!");
                }
                eagle = new Tile(TileType.EAGLE, new Point2D(posX, posY), new Point2D(sizeX, sizeY));
                getEntityList().add(eagle);
            }
            if (line[0].equals("leaves")) {
                getEntityList().add(new Tile(TileType.LEAVES, new Point2D(posX, posY), new Point2D(sizeX, sizeY)));
            }
        }
        getEntityList().add(playerTank);
        getEntityList().add(new EnemyTank(200, 100, 36, 36));
        getEntityList().add(new EnemyTank(300, 100, 36, 36));
    }

    @Override
    public void update() {
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> newEntities = new ArrayList<>();
        for (var entity : getEntityList()) {
            entity.update();
            List<Notification> notificationList = entity.getNotificationList();
            for (int i = 0; i < notificationList.size(); i++) {
                var notification = notificationList.get(i);
                switch (notification.getContext()) {
                    case DESTROY_SELF -> toRemove.add(entity);
                    case MOVE -> {
                        if (entity instanceof EntityMovable) {
                            moveEntity((EntityMovable) entity, notification.getDirection());
                        }
                    }
                    case SHOOT -> {
                        if (entity instanceof Tank) {
                            newEntities.add(new Bullet((Tank) entity));
                            entity.update();
                        }
                    }
                }
            }
            notificationList.clear();
        }
        for (var entity : toRemove) {
            if (entity != null) {
                if (entity == eagle) {
                    getNotificationList().add(new Notification(Context.GAME_LOST, null));
                }
                if (entity instanceof  EnemyTank) {
                    tanksKilled++;
                }
                getEntityList().remove(entity);
            }
        }
        toRemove.clear();
        getEntityList().addAll(newEntities);
        getEntityList().sort((lhs, rhs) -> {
            boolean lhsSolid = true, rhsSolid = true;
            if (lhs instanceof Tile) {
                Tile tile = (Tile) lhs;
                lhsSolid = tile.getTileType().isSolid();
            }
            if (rhs instanceof Tile) {
                Tile tile = (Tile) rhs;
                rhsSolid = tile.getTileType().isSolid();
            }
            return lhsSolid ? (rhsSolid ? 0 : -1) : (rhsSolid ? 1 : 0);
        });

        if (tanksKilled >= 2) {
            getNotificationList().add(new Notification(Context.GAME_WON, null));
        }
    }

    private boolean moveEntity(EntityMovable entity, Direction dir) {
        Point2D offset = Direction.convertDirectionToOffset(dir);
        Point2D curPos = entity.getPosition();
        Point2D curSize = entity.getSize();
        Point2D newPos = new Point2D(curPos.getX() + entity.getSpeed() * offset.getX(), curPos.getY() + entity.getSpeed() * offset.getY());
        for (var otherEntity : getEntityList()) {
            if (otherEntity == entity) {
                continue;
            }
            if (otherEntity instanceof Tile) {
                Tile tile = (Tile) otherEntity;
                if (!tile.getTileType().isSolid()) {
                    continue;
                }
            }
            if (entity.canGoThrough(otherEntity)) {
                continue;
            }

            Point2D otherPos = otherEntity.getPosition();
            Point2D otherSize = otherEntity.getSize();
            if (otherPos.getX() < newPos.getX() + curSize.getX() &&
                otherPos.getX() + otherSize.getX() > newPos.getX() &&
                otherPos.getY() < newPos.getY() + curSize.getY() &&
                otherPos.getY() + otherSize.getY() > newPos.getY()) {
                otherEntity.onCollideWith(entity);
                entity.onCollideWith(otherEntity);
                return false;
            }
        }
        entity.setPosition(newPos);
        return true;
    }

    @Override
    public boolean action(Action action) {
        boolean result = false;
        switch (action) {
            case UP -> result = move(Direction.UP);
            case DOWN -> result = move(Direction.DOWN);
            case LEFT -> result = move(Direction.LEFT);
            case RIGHT -> result = move(Direction.RIGHT);
            case ACTION -> result = shoot();
            case ESCAPE -> {
                result = true;
                getNotificationList().add(new Notification(Context.TO_MAIN_MENU, null));
            }
        }
        return result;
    }

    private boolean move(Direction direction) {
        return playerTank.move(direction);
    }

    private boolean shoot() {
        playerTank.shoot();
        return true;
    }
}
