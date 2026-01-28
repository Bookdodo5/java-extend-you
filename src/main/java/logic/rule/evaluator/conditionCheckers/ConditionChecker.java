package logic.rule.evaluator.conditionCheckers;

import model.entity.Entity;
import model.map.LevelMap;
import model.rule.Condition;
import model.rule.Ruleset;

/**
 * Interface for implementing a condition word checker.<br>
 * Defines a method to check if a specific condition is satisfied for a given entity within a level map and ruleset.
 */
public interface ConditionChecker {
    boolean isSatisfied(Entity entity, Condition condition, LevelMap levelMap, Ruleset ruleset);
}
