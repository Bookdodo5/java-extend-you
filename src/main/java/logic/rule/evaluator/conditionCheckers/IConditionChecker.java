package logic.rule.evaluator.conditionCheckers;

import model.entity.Entity;
import model.map.LevelMap;
import model.rule.Condition;

public interface IConditionChecker {
    boolean isSatisfied(Entity entity, Condition condition, LevelMap levelMap);
}
