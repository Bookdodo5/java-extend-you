package logic.controller.turn;

import logic.rule.evaluator.RuleEvaluator;
import model.action.Action;
import model.action.CompositeAction;
import model.action.CreateAction;
import model.action.DestroyAction;
import model.entity.Entity;
import model.entity.EntityType;
import model.entity.TypeRegistry;
import model.map.LevelMap;
import model.rule.Ruleset;
import model.rule.Transformation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InteractionHandler {

    public CompositeAction handleInteractions(LevelMap levelMap, Ruleset ruleset, RuleEvaluator ruleEvaluator) {
        CompositeAction action = new CompositeAction();
        processTransformation(levelMap, ruleset, ruleEvaluator, action);
        processSink(levelMap, ruleset, ruleEvaluator, action);
        processYouDefeat(levelMap, ruleset, ruleEvaluator, action);
        processHotMelt(levelMap, ruleset, ruleEvaluator, action);
        processHas(levelMap, ruleset, ruleEvaluator, action);
        return action;
    }

    private void processTransformation(LevelMap levelMap, Ruleset ruleset, RuleEvaluator ruleEvaluator, CompositeAction action) {
        ruleEvaluator.getTransformations(levelMap, ruleset).forEach(transformation -> {
            Entity source = transformation.getSource();
            EntityType targetType = transformation.getTargetType();
            action.add(new CreateAction(levelMap, targetType, source.getPosX(), source.getPosY()));
            action.add(new DestroyAction(levelMap, source));
        });
    }

    private void processYouDefeat(LevelMap levelMap, Ruleset ruleset, RuleEvaluator ruleEvaluator, CompositeAction action) {
        List<Entity> youEntities = ruleEvaluator.getEntitiesWithProperty(TypeRegistry.YOU, levelMap, ruleset);
        for (Entity youEntity : youEntities) {
            int x = youEntity.getPosX();
            int y = youEntity.getPosY();
            if (ruleEvaluator.hasEntityWithPropertyAt(TypeRegistry.DEFEAT, levelMap, ruleset, x, y)) {
                action.add(new DestroyAction(levelMap, youEntity));
            }
        }
    }

    private void processHotMelt(LevelMap levelMap, Ruleset ruleset, RuleEvaluator ruleEvaluator, CompositeAction action) {
        List<Entity> meltEntities = ruleEvaluator.getEntitiesWithProperty(TypeRegistry.MELT, levelMap, ruleset);
        for (Entity meltEntity : meltEntities) {
            int x = meltEntity.getPosX();
            int y = meltEntity.getPosY();
            if (ruleEvaluator.hasEntityWithPropertyAt(TypeRegistry.HOT, levelMap, ruleset, x, y)) {
                action.add(new DestroyAction(levelMap, meltEntity));
            }
        }
    }

    private void processSink(LevelMap levelMap, Ruleset ruleset, RuleEvaluator ruleEvaluator, CompositeAction action) {
        List<Entity> sinkEntities = ruleEvaluator.getEntitiesWithProperty(TypeRegistry.SINK, levelMap, ruleset);
        Set<String> processedPositions = new HashSet<>();

        for (Entity sinkEntity : sinkEntities) {
            int x = sinkEntity.getPosX();
            int y = sinkEntity.getPosY();
            String positionKey = x + "," + y;

            if (processedPositions.contains(positionKey)) {
                continue;
            }

            List<Entity> entitiesAtPosition = levelMap.getEntitiesAt(x, y);
            if (entitiesAtPosition.size() > 1) {
                for (Entity entity : entitiesAtPosition) {
                    action.add(new DestroyAction(levelMap, entity));
                }
                processedPositions.add(positionKey);
            }
        }
    }

    private void processHas(LevelMap levelMap, Ruleset ruleset, RuleEvaluator ruleEvaluator, CompositeAction action) {
        List<Action> destroyActions = action.getActions().stream()
                .filter(a -> a instanceof DestroyAction)
                .toList();
        List<Transformation> hasTransformations = ruleEvaluator.getHasTransformations(levelMap, ruleset);
        for (Transformation transformation : hasTransformations) {
            Entity source = transformation.getSource();
            EntityType targetType = transformation.getTargetType();
            destroyActions.stream()
                    .filter(a -> a instanceof DestroyAction)
                    .map(a -> (DestroyAction) a)
                    .filter(a -> a.getEntity() == source)
                    .forEach(a -> {
                        int posX = a.getEntity().getPosX();
                        int posY = a.getEntity().getPosY();
                        action.add(new CreateAction(levelMap, targetType, posX, posY));
                    });
        }
    }
}
