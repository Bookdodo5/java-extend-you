package logic.rule.evaluator;

import model.entity.Entity;
import model.entity.EntityType;
import model.entity.TypeRegistry;
import model.map.LevelMap;
import model.rule.Rule;
import model.rule.Ruleset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Finds out if an entity is an instance of a specific type, considering inheritance rules "X EXTENDS Y".
 */
public class InheritanceResolver {
    private final ConditionEvaluator conditionEvaluator;

    public InheritanceResolver() {
        this.conditionEvaluator = new ConditionEvaluator();
    }

    public boolean isInstanceOf(Entity entity, EntityType targetType, LevelMap levelMap, Ruleset ruleset) {
        List<Rule> extendRules = ruleset.getRules().stream()
                .filter(rule -> rule.getVerb() == TypeRegistry.EXTEND)
                .toList();
        Set<EntityType> visitedTypes = new HashSet<>();
        return isInstanceOfRecursive(entity, entity.getType(), targetType, extendRules, visitedTypes, levelMap, ruleset);
    }

    private boolean isInstanceOfRecursive(Entity entity, EntityType currentType, EntityType targetType,
                                         List<Rule> extendRules, Set<EntityType> visitedTypes,
                                         LevelMap levelMap, Ruleset ruleset) {

        if (currentType.equals(targetType)) {
            return true;
        }

        if (visitedTypes.contains(currentType)) {
            return false;
        }

        visitedTypes.add(currentType);

        for (Rule rule : extendRules) {
            if(!rule.getSubject().equals(currentType)) {
                continue;
            }
            if(visitedTypes.contains(rule.getEffect())) {
                continue;
            }
            if(!conditionEvaluator.evaluate(entity, rule.getConditions(), levelMap, ruleset)) {
                continue;
            }
            if (isInstanceOfRecursive(entity, rule.getEffect(), targetType, extendRules, visitedTypes, levelMap, ruleset)) {
                return true;
            }
        }

        return false;
    }
}
