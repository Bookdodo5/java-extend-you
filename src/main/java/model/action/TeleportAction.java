package model.action;

import model.entity.Entity;
import model.map.LevelMap;

public class TeleportAction implements Action {

    private final LevelMap levelMap;
    private final Entity entity;
    private final int endX, endY;
    private int startX, startY;

    public TeleportAction(LevelMap levelMap, Entity entity, int endX, int endY) {
        this.levelMap = levelMap;
        this.entity = entity;
        this.endX = endX;
        this.endY = endY;
        startX = entity.getPosX();
        startY = entity.getPosY();
    }

    @Override
    public void execute() {
        startX = entity.getPosX();
        startY = entity.getPosY();
        levelMap.setEntityPosition(entity, endX, endY);
    }

    @Override
    public void undo() {
        levelMap.setEntityPosition(entity, startX, startY);
    }
}