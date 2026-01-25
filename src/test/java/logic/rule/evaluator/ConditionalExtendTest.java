package logic.rule.evaluator;

import model.entity.Entity;
import model.entity.TypeRegistry;
import model.map.LevelMap;
import model.rule.Condition;
import model.rule.Rule;
import model.rule.Ruleset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalExtendTest {
    private RuleEvaluator evaluator;
    private LevelMap levelMap;
    private Ruleset ruleset;

    @BeforeEach
    void setUp() {
        evaluator = new RuleEvaluator();
        levelMap = new LevelMap(10, 10);
        ruleset = new Ruleset();
    }

    @Test
    void testExtendWithOnCondition() {
        // Create entities: JAVA, FLAG, PAPER
        Entity java = new Entity(TypeRegistry.JAVA, 5, 5);
        Entity flag = new Entity(TypeRegistry.FLAG, 5, 5); // Same position as JAVA
        Entity paper = new Entity(TypeRegistry.PAPER, 3, 3);

        levelMap.addEntity(java);
        levelMap.addEntity(flag);
        levelMap.addEntity(paper);

        // Create rule: JAVA ON FLAG EXTEND PAPER
        Entity javaText = new Entity(TypeRegistry.TEXT_JAVA, 0, 0);
        Entity onText = new Entity(TypeRegistry.ON, 1, 0);
        Entity flagText = new Entity(TypeRegistry.TEXT_FLAG, 2, 0);
        Entity extendText = new Entity(TypeRegistry.EXTEND, 3, 0);
        Entity paperText = new Entity(TypeRegistry.TEXT_PAPER, 4, 0);

        Condition onFlag = new Condition(onText, flagText);
        Rule javaOnFlagExtendsPaper = new Rule(javaText, extendText, paperText, List.of(onFlag));
        ruleset.setRules(List.of(javaOnFlagExtendsPaper));

        // JAVA is on FLAG, so JAVA should extend PAPER
        InheritanceResolver resolver = new InheritanceResolver();
        assertTrue(resolver.isInstanceOf(java, TypeRegistry.PAPER, levelMap, ruleset));

        // Move JAVA away from FLAG
        levelMap.setEntityPosition(java, 7, 7);

        // Now JAVA is not on FLAG, so JAVA should NOT extend PAPER
        assertFalse(resolver.isInstanceOf(java, TypeRegistry.PAPER, levelMap, ruleset));
    }

    @Test
    void testExtendWithOnConditionAndProperty() {
        // Create entities
        Entity java = new Entity(TypeRegistry.JAVA, 5, 5);
        Entity flag = new Entity(TypeRegistry.FLAG, 5, 5); // Same position as JAVA

        levelMap.addEntity(java);
        levelMap.addEntity(flag);

        // Create rule: JAVA ON FLAG EXTEND PAPER
        Entity javaText = new Entity(TypeRegistry.TEXT_JAVA, 0, 0);
        Entity onText = new Entity(TypeRegistry.ON, 1, 0);
        Entity flagText = new Entity(TypeRegistry.TEXT_FLAG, 2, 0);
        Entity extendText = new Entity(TypeRegistry.EXTEND, 3, 0);
        Entity paperText = new Entity(TypeRegistry.TEXT_PAPER, 4, 0);

        Condition onFlag = new Condition(onText, flagText);
        Rule javaOnFlagExtendsPaper = new Rule(javaText, extendText, paperText, List.of(onFlag));

        // Create rule: PAPER IS PUSH
        Entity isText = new Entity(TypeRegistry.IS, 5, 0);
        Entity pushText = new Entity(TypeRegistry.PUSH, 6, 0);
        Rule paperIsPush = new Rule(paperText, isText, pushText, List.of());

        ruleset.setRules(List.of(javaOnFlagExtendsPaper, paperIsPush));

        // JAVA is on FLAG and extends PAPER, so JAVA should have PUSH property
        assertTrue(evaluator.hasProperty(java, TypeRegistry.PUSH, levelMap, ruleset),
                "JAVA on FLAG should have PUSH property through PAPER inheritance");

        // Move JAVA away from FLAG
        levelMap.setEntityPosition(java, 7, 7);

        // Now JAVA should NOT have PUSH property
        assertFalse(evaluator.hasProperty(java, TypeRegistry.PUSH, levelMap, ruleset),
                "JAVA not on FLAG should NOT have PUSH property");
    }

    @Test
    void testExtendWithNearCondition() {
        // Create entities
        Entity java = new Entity(TypeRegistry.JAVA, 5, 5);
        Entity flag = new Entity(TypeRegistry.FLAG, 5, 6); // Near JAVA

        levelMap.addEntity(java);
        levelMap.addEntity(flag);

        // Create rule: JAVA NEAR FLAG EXTEND PAPER
        Entity javaText = new Entity(TypeRegistry.TEXT_JAVA, 0, 0);
        Entity nearText = new Entity(TypeRegistry.NEAR, 1, 0);
        Entity flagText = new Entity(TypeRegistry.TEXT_FLAG, 2, 0);
        Entity extendText = new Entity(TypeRegistry.EXTEND, 3, 0);
        Entity paperText = new Entity(TypeRegistry.TEXT_PAPER, 4, 0);

        Condition nearFlag = new Condition(nearText, flagText);
        Rule javaNearFlagExtendsPaper = new Rule(javaText, extendText, paperText, List.of(nearFlag));
        ruleset.setRules(List.of(javaNearFlagExtendsPaper));

        // JAVA is near FLAG
        InheritanceResolver resolver = new InheritanceResolver();
        assertTrue(resolver.isInstanceOf(java, TypeRegistry.PAPER, levelMap, ruleset),
                "JAVA near FLAG should be instanceof PAPER");

        // Move FLAG far away
        levelMap.setEntityPosition(flag, 0, 0);

        // Now JAVA is not near FLAG
        assertFalse(resolver.isInstanceOf(java, TypeRegistry.PAPER, levelMap, ruleset),
                "JAVA not near FLAG should NOT be instanceof PAPER");
    }

    @Test
    void testExtendChainWithConditions() {
        // Create entities
        Entity java = new Entity(TypeRegistry.JAVA, 5, 5);
        Entity flag = new Entity(TypeRegistry.FLAG, 5, 5); // Same position as JAVA
        Entity python = new Entity(TypeRegistry.PYTHON, 3, 3);

        levelMap.addEntity(java);
        levelMap.addEntity(flag);
        levelMap.addEntity(python);

        // Create rules: JAVA ON FLAG EXTEND PYTHON, PYTHON EXTEND PAPER
        Entity javaText = new Entity(TypeRegistry.TEXT_JAVA, 0, 0);
        Entity onText = new Entity(TypeRegistry.ON, 1, 0);
        Entity flagText = new Entity(TypeRegistry.TEXT_FLAG, 2, 0);
        Entity extendText = new Entity(TypeRegistry.EXTEND, 3, 0);
        Entity pythonText = new Entity(TypeRegistry.TEXT_PYTHON, 4, 0);
        Entity paperText = new Entity(TypeRegistry.TEXT_PAPER, 5, 0);

        Condition onFlag = new Condition(onText, flagText);
        Rule javaOnFlagExtendsPython = new Rule(javaText, extendText, pythonText, List.of(onFlag));
        Rule pythonExtendsPaper = new Rule(pythonText, extendText, paperText, List.of());

        ruleset.setRules(List.of(javaOnFlagExtendsPython, pythonExtendsPaper));

        // JAVA is on FLAG, so JAVA -> PYTHON -> PAPER
        InheritanceResolver resolver = new InheritanceResolver();
        assertTrue(resolver.isInstanceOf(java, TypeRegistry.PYTHON, levelMap, ruleset),
                "JAVA on FLAG should be instanceof PYTHON");
        assertTrue(resolver.isInstanceOf(java, TypeRegistry.PAPER, levelMap, ruleset),
                "JAVA on FLAG should be instanceof PAPER through PYTHON");

        // Move JAVA away from FLAG
        levelMap.setEntityPosition(java, 7, 7);

        // Now JAVA should not extend anything
        assertFalse(resolver.isInstanceOf(java, TypeRegistry.PYTHON, levelMap, ruleset),
                "JAVA not on FLAG should NOT be instanceof PYTHON");
        assertFalse(resolver.isInstanceOf(java, TypeRegistry.PAPER, levelMap, ruleset),
                "JAVA not on FLAG should NOT be instanceof PAPER");
    }
}
