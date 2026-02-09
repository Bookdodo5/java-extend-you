package logic.rule.evaluator.conditionCheckers;

import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.map.LevelMap;
import model.rule.Condition;
import model.rule.Ruleset;

import java.util.List;

/** Checks for "A FACING B IS X" conditions. */
public class FacingChecker implements ConditionChecker {
    @Override
    public boolean isSatisfied(Entity entity, Condition condition, LevelMap levelMap, Ruleset ruleset) {
        EntityType targetNear = condition.getParameter();
        Direction facing = entity.getDirection();
        int checkX = levelMap.getX(entity) + facing.dx;
        int checkY = levelMap.getY(entity) + facing.dy;
        List<Entity> entitiesToCheck = levelMap.getEntitiesAt(checkX, checkY);
        return entitiesToCheck.stream()
                .anyMatch(e -> e.getType() == targetNear && e != entity);
    }
}
