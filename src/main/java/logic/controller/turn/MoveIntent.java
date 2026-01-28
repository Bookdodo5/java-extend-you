package logic.controller.turn;

import model.entity.Direction;
import model.entity.Entity;

import java.util.Objects;

/**
 * Represents an intent to move an entity in a specified direction.
 */
public class MoveIntent {
    private final Entity entity;
    private final Direction direction;
    private final boolean isFromMove;

    /**
     * Constructs a MoveIntent with the specified entity, direction, and source flag.
     *
     * @param entity     the entity that intends to move
     * @param direction  the direction in which the entity intends to move
     * @param isFromMove flag indicating if the intent comes from a rule "X IS MOVE", which need to handle bouncing.
     */
    public MoveIntent(Entity entity, Direction direction, boolean isFromMove) {
        this.entity = entity;
        this.direction = direction;
        this.isFromMove = isFromMove;
    }

    public Entity getEntity() {
        return entity;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isFromMove() {
        return isFromMove;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MoveIntent that = (MoveIntent) obj;
        return Objects.equals(entity, that.entity) && direction == that.direction && isFromMove == that.isFromMove;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, direction, isFromMove);
    }
}
