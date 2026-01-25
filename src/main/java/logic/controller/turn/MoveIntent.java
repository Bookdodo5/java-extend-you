package logic.controller.turn;

import model.entity.Direction;
import model.entity.Entity;

import java.util.Objects;

public class MoveIntent {
    private final Entity entity;
    private final Direction direction;
    private final boolean isFromMove;

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
