package logic.controller.turn;

import logic.rule.evaluator.RuleEvaluator;
import model.action.CompositeAction;
import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.entity.TypeRegistry;
import model.map.LevelMap;
import model.rule.Rule;
import model.rule.Ruleset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollisionResolverTest {
    private CollisionResolver collisionResolver;
    private RuleEvaluator ruleEvaluator;
    private LevelMap levelMap;
    private final List<Rule> rules = new ArrayList<>();

    @BeforeEach
    void setUp() {
        collisionResolver = new CollisionResolver();
        ruleEvaluator = new RuleEvaluator();
        levelMap = new LevelMap(10, 10);
        rules.clear();
    }

    private Entity addEntity(EntityType type, int x, int y, Direction direction) {
        Entity entity = new Entity(type, x, y);
        entity.setDirection(direction);
        levelMap.addEntity(entity);
        return entity;
    }

    private Entity addEntity(EntityType type, int x, int y) {
        return addEntity(type, x, y, Direction.RIGHT);
    }

    private void rule(EntityType subject, EntityType property) {
        Entity subjectEntity = new Entity(subject, 0, rules.size());
        Entity is = new Entity(TypeRegistry.IS, 1, rules.size());
        Entity propertyEntity = new Entity(property, 2, rules.size());
        rules.add(new Rule(subjectEntity, is, propertyEntity, List.of()));
    }

    private CompositeAction resolveCollisions(List<MoveIntent> intents) {
        Ruleset ruleset = new Ruleset();
        ruleset.setRules(rules);
        return collisionResolver.resolveCollisions(intents, levelMap, ruleset, ruleEvaluator);
    }

    @Test
    void testResolveCollisionsNoIntents() {
        CompositeAction result = resolveCollisions(List.of());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testResolveCollisionsSimpleMove() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        assertEquals(6, javaEntity.getPosX());
        assertEquals(5, javaEntity.getPosY());
    }

    @Test
    void testResolveCollisionsMoveToBoundary() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 0, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.LEFT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // Should not move outside boundary
        assertEquals(0, javaEntity.getPosX());
        assertEquals(5, javaEntity.getPosY());
    }

    @Test
    void testResolveCollisionsWithStop() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.STOP);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // Should not move into STOP entity
        assertEquals(5, javaEntity.getPosX());
        assertEquals(5, javaEntity.getPosY());
    }

    @Test
    void testResolveCollisionsWithPush() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.PUSH);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // JAVA should move to (6, 5) and PYTHON should be pushed to (7, 5)
        assertEquals(6, javaEntity.getPosX());
        assertEquals(5, javaEntity.getPosY());
        assertEquals(7, pythonEntity.getPosX());
        assertEquals(5, pythonEntity.getPosY());
    }

    @Test
    void testResolveCollisionsWithPushChain() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        Entity paperEntity = addEntity(TypeRegistry.PAPER, 7, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.PUSH);
        rule(TypeRegistry.TEXT_PAPER, TypeRegistry.PUSH);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // All should be pushed
        assertEquals(6, javaEntity.getPosX());
        assertEquals(7, pythonEntity.getPosX());
        assertEquals(8, paperEntity.getPosX());
    }

    @Test
    void testResolveCollisionsWithPushIntoStop() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        Entity paperEntity = addEntity(TypeRegistry.PAPER, 7, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.PUSH);
        rule(TypeRegistry.TEXT_PAPER, TypeRegistry.STOP);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // Nothing should move because push is blocked by STOP
        assertEquals(5, javaEntity.getPosX());
        assertEquals(6, pythonEntity.getPosX());
    }

    @Test
    void testResolveCollisionsWithPushIntoBoundary() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 8, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 9, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.PUSH);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // Nothing should move because push would go outside boundary
        assertEquals(8, javaEntity.getPosX());
        assertEquals(9, pythonEntity.getPosX());
    }

    @Test
    void testResolveCollisionsWithMovePropertyBounce() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5, Direction.RIGHT);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.MOVE);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.STOP);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, true);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // JAVA should bounce back to (4, 5) and face LEFT
        assertEquals(4, javaEntity.getPosX());
        assertEquals(5, javaEntity.getPosY());
        assertEquals(Direction.LEFT, javaEntity.getDirection());
    }

    @Test
    void testResolveCollisionsWithMovePropertyBounceIntoStop() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5, Direction.RIGHT);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        Entity paperEntity = addEntity(TypeRegistry.PAPER, 4, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.MOVE);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.STOP);
        rule(TypeRegistry.TEXT_PAPER, TypeRegistry.STOP);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, true);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // JAVA should rotate but not move (STOP at both sides)
        assertEquals(5, javaEntity.getPosX());
        assertEquals(Direction.LEFT, javaEntity.getDirection());
    }

    @Test
    void testResolveCollisionsWithMovePropertyBounceIntoPush() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5, Direction.RIGHT);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        Entity paperEntity = addEntity(TypeRegistry.PAPER, 4, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.MOVE);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.STOP);
        rule(TypeRegistry.TEXT_PAPER, TypeRegistry.PUSH);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, true);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // JAVA should rotate and push PAPER
        assertEquals(4, javaEntity.getPosX());
        assertEquals(Direction.LEFT, javaEntity.getDirection());
        assertEquals(3, paperEntity.getPosX());
    }

    @Test
    void testResolveCollisionsCreatingEntityStack() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 7, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.MOVE);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.MOVE);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.STOP);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.STOP);

        MoveIntent intent1 = new MoveIntent(javaEntity, Direction.RIGHT, true);
        MoveIntent intent2 = new MoveIntent(pythonEntity, Direction.LEFT, true);

        CompositeAction result1 = resolveCollisions(List.of(intent1, intent2));
        result1.execute();

        // Both should move towards each other
        assertEquals(6, javaEntity.getPosX());
        assertEquals(6, pythonEntity.getPosX());

        CompositeAction result2 = resolveCollisions(List.of(intent1, intent2));
        result2.execute();

        // Both should move past each other
        assertEquals(7, javaEntity.getPosX());
        assertEquals(5, pythonEntity.getPosX());
    }

    @Test
    void testResolveCollisionsPushAndStop() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 6, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.PUSH);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.STOP);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // PYTHON has both PUSH and STOP, so it should be pushable
        assertEquals(6, javaEntity.getPosX());
        assertEquals(7, pythonEntity.getPosX());
    }

    @Test
    void testResolveCollisionsYouAndStop() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 0, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 1, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.STOP);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.STOP);

        MoveIntent intent1 = new MoveIntent(javaEntity, Direction.LEFT, false);
        MoveIntent intent2 = new MoveIntent(pythonEntity, Direction.LEFT, false);
        CompositeAction result = resolveCollisions(List.of(intent1, intent2));
        result.execute();

        // Nothing should move because both are STOP
        assertEquals(0, javaEntity.getPosX());
        assertEquals(1, pythonEntity.getPosX());
    }


    @Test
    void testResolveCollisionsYouStacking() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 0, 5);
        Entity pythonEntity = addEntity(TypeRegistry.PYTHON, 1, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);
        rule(TypeRegistry.TEXT_PYTHON, TypeRegistry.YOU);

        MoveIntent intent1 = new MoveIntent(javaEntity, Direction.LEFT, false);
        MoveIntent intent2 = new MoveIntent(pythonEntity, Direction.LEFT, false);
        CompositeAction result = resolveCollisions(List.of(intent1, intent2));
        result.execute();

        // Both should stack together at (0, 5)
        assertEquals(0, javaEntity.getPosX());
        assertEquals(0, pythonEntity.getPosX());
    }

    @Test
    void testResolveCollisionsEmptySpace() {
        Entity javaEntity = addEntity(TypeRegistry.JAVA, 5, 5);
        rule(TypeRegistry.TEXT_JAVA, TypeRegistry.YOU);

        MoveIntent intent = new MoveIntent(javaEntity, Direction.RIGHT, false);
        CompositeAction result = resolveCollisions(List.of(intent));
        result.execute();

        // Should move normally into empty space
        assertEquals(6, javaEntity.getPosX());
    }
}
