package logic.rule.evaluator.conditionCheckers;

import model.entity.Entity;
import model.map.LevelMap;
import model.rule.Condition;
import model.rule.Ruleset;

public interface ConditionChecker {
    boolean isSatisfied(Entity entity, Condition condition, LevelMap levelMap, Ruleset ruleset);
}
