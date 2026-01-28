package logic.rule.evaluator.conditionCheckers;

import logic.rule.evaluator.InheritanceResolver;
import model.entity.Entity;
import model.entity.EntityType;
import model.map.LevelMap;
import model.rule.Condition;
import model.rule.Ruleset;

/** Checks for "A INSTANCEOF B IS X" conditions. */
public class InstanceofChecker implements ConditionChecker {

    @Override
    public boolean isSatisfied(Entity entity, Condition condition, LevelMap levelMap, Ruleset ruleset) {
        InheritanceResolver inheritanceResolver = new InheritanceResolver();
        EntityType targetType = condition.getParameter();
        return inheritanceResolver.isInstanceOf(entity, targetType, levelMap, ruleset);
    }
}
