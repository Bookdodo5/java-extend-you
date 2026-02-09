package model.action;

import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.map.LevelMap;

/**
 * Action to create a new entity at a specified position on the level map.
 */
public class CreateAction implements Action {

    private final LevelMap levelMap;
    private final Entity entity;
    private final int posX;
    private final int posY;

    public CreateAction(LevelMap levelMap, EntityType entityType, int posX, int posY) {
        this(levelMap, entityType, Direction.DOWN, posX, posY);
    }

    public CreateAction(LevelMap levelMap, EntityType entityType, Direction direction, int posX, int posY) {
        this.levelMap = levelMap;
        this.posX = posX;
        this.posY = posY;
        this.entity = new Entity(entityType);
        entity.setDirection(direction);
    }

    @Override
    public void execute() {
        levelMap.setPosition(entity, posX, posY);
    }

    @Override
    public void undo() {
        levelMap.removeEntity(entity);
    }
}
