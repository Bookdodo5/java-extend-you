package logic.rule.evaluator;

import logic.rule.evaluator.conditionCheckers.FacingChecker;
import logic.rule.evaluator.conditionCheckers.IConditionChecker;
import logic.rule.evaluator.conditionCheckers.NearChecker;
import logic.rule.evaluator.conditionCheckers.OnChecker;
import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.entity.TypeRegistry;
import model.map.LevelMap;
import model.rule.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionEvaluator {
    private final Map<EntityType, IConditionChecker> conditionCheckers = new HashMap<>();

    public ConditionEvaluator() {
        conditionCheckers.put(TypeRegistry.ON, new OnChecker());
        conditionCheckers.put(TypeRegistry.FACING, new FacingChecker());
        conditionCheckers.put(TypeRegistry.NEAR, new NearChecker());
    }

    public boolean evaluate(Entity entity, List<Condition> conditions, LevelMap levelMap) {
        for (Condition condition : conditions) {
            IConditionChecker checker = conditionCheckers.get(condition.getCondition());
            if (checker == null || !checker.isSatisfied(entity, condition, levelMap)) {
                return false;
            }
        }
        return true;
    }
}
