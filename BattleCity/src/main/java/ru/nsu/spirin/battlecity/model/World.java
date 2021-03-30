package ru.nsu.spirin.battlecity.model;

import ru.nsu.spirin.battlecity.exceptions.InvalidBattleGridException;
import ru.nsu.spirin.battlecity.math.Direction;
import ru.nsu.spirin.battlecity.math.Point2D;
import ru.nsu.spirin.battlecity.model.notification.Notification;
import ru.nsu.spirin.battlecity.model.tank.Tank;
import ru.nsu.spirin.battlecity.model.tiles.TileBrick;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class World {
    private final Tank playerTank;
    private final List<Entity> entityList = new ArrayList<>();

    public World(String mapFileName, Tank playerTank) throws IOException, InvalidBattleGridException {
        this.playerTank = playerTank;

        Scanner scanner = new Scanner(new FileInputStream(mapFileName));
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");
            if (line.length == 0) {
                throw new InvalidBattleGridException("");
            }
            switch(line[0]) {
                case "block" -> {
                    if (line.length != 6) {
                        throw new InvalidBattleGridException("");
                    }
                    if (line[1].equals("brick")) {
                        int posX = Integer.parseInt(line[2]);
                        int posY = Integer.parseInt(line[3]);
                        int sizeX = Integer.parseInt(line[4]);
                        int sizeY = Integer.parseInt(line[5]);
                        entityList.add(new TileBrick(posX, posY, sizeX, sizeY));
                    }
                }
            }
        }
        entityList.add(playerTank);
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void update() {
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> newEntities = new ArrayList<>();
        for (var entity : entityList) {
            entity.update();
            List<Notification> notificationList = entity.getNotificationList();
            for (var notification : notificationList) {
                switch (notification.getContext()) {
                    case DESTROY_SELF -> {
                        toRemove.add(entity);
                    }
                    case MOVE -> {
                        if (entity instanceof EntityMovable) {
                            moveEntity((EntityMovable) entity, notification.getDirection());
                        }
                    }
                    case SHOOT -> {
                        if (entity instanceof Tank) {
                            Point2D tankPos = entity.getPosition();
                            Point2D tankSize = entity.getSize();
                            newEntities.add(new Bullet(tankPos.getX() + tankSize.getX() / 2 - 25 / 2, tankPos.getY() + tankSize.getY() / 2 - 25 / 2, 25, 25, playerTank.getDirection()));
                            entity.update();
                        }
                    }
                }
            }
            notificationList.clear();
        }
        for (var entity : toRemove) {
            entityList.remove(entity);
        }
        entityList.addAll(newEntities);
    }

    private boolean moveEntity(EntityMovable entity, Direction dir) {
        Point2D offset = Direction.convertDirectionToOffset(dir);
        Point2D curPos = entity.getPosition();
        Point2D newPos = new Point2D(curPos.getX() + entity.getSpeed() * offset.getX(), curPos.getY() + entity.getSpeed() * offset.getY());
        for (var otherEntity : entityList) {
            if (otherEntity == entity) {
                continue;
            }

            Point2D otherPos = otherEntity.getPosition();
            Point2D otherSize = otherEntity.getSize();
            if (otherPos.getX() <= newPos.getX() && newPos.getX() < otherPos.getX() + otherSize.getX() &&
                    otherPos.getY() <= newPos.getY() && newPos.getY() < otherPos.getY() + otherSize.getY()) {
                return false;
            }
        }
        entity.setPosition(newPos);
        return true;
    }
}
