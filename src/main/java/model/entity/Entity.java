package model.entity;

import java.util.UUID;

/**
 * Represents any entity in the game, which is anything than exist in a level map: objects, words, etc.
 * <p>Each entity has a type which identifies what it is.
 * This is done to generalize and abstract away the identity from the calculation.</p>
 * <p>With the type object, it is easier to code in dynamic rules, and it let me have only one class instead of 50-60 classes for each entity.</p>
 */
public class Entity {

    private final UUID entityId;
    private final EntityType entityType;
    private Direction direction;

    public Entity(EntityType entityType) {
        this.entityId = UUID.randomUUID();
        this.entityType = entityType;
        this.direction = Direction.DOWN;
    }

    public Entity(Entity other) {
        this.entityId = other.entityId;
        this.entityType = other.entityType;
        this.direction = other.direction;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public EntityType getType() {
        return entityType;
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}