package logic.rule.evaluator.conditionCheckers;

import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.map.LevelMap;
import model.rule.Condition;

import java.util.List;

public class FacingChecker implements ConditionChecker {
    @Override
    public boolean isSatisfied(Entity entity, Condition condition, LevelMap levelMap) {
        EntityType targetNear = condition.getParameter();
        Direction facing = entity.getDirection();
        int checkX = entity.getPosX() + facing.dx;
        int checkY = entity.getPosY() + facing.dy;
        List<Entity> entitiesToCheck = levelMap.getEntitiesAt(checkX, checkY);
        return entitiesToCheck.stream()
                .anyMatch(e -> e.getType() == targetNear && e != entity);
    }
}
