package logic;

import logic.input.InputUtility;
import javafx.scene.input.KeyCode;
import logic.rule.evaluator.RuleEvaluator;
import logic.rule.parser.RuleParser;
import model.entity.Direction;
import model.entity.Entity;
import model.entity.TypeRegistry;
import model.map.LevelMap;
import model.rule.Ruleset;

import java.util.List;

public class TurnLogic {
    private LevelMap levelMap;
    private final Ruleset ruleset = new Ruleset();
    private final RuleParser ruleParser = new RuleParser();
    private final RuleEvaluator ruleEvaluator = new RuleEvaluator();

    public void update() {

        // 1. Handle input
        Direction playerMove = getPlayerInput();
        // If player pressed nothing and SPACE is not pressed, skip the turn processing
        if(!InputUtility.isPressed(KeyCode.SPACE) && playerMove == null) {

            // Player inputs nothing -> skip
            return;
        }

        // 2. Process YOU moves
        List<Entity> youEntities = ruleEvaluator.getEntitiesWithProperty(
                TypeRegistry.YOU, levelMap, ruleset
        );
        if(youEntities.isEmpty()) {

            // There is no you -> set flag for graphic display
            return;
        }

        boolean hasTextMoved = false;
        for(Entity youEntity : youEntities) {

        }
    }

    private Direction getPlayerInput() {
        if (InputUtility.isPressed(KeyCode.W)) return Direction.UP;
        if (InputUtility.isPressed(KeyCode.S)) return Direction.DOWN;
        if (InputUtility.isPressed(KeyCode.A)) return Direction.LEFT;
        if (InputUtility.isPressed(KeyCode.D)) return Direction.RIGHT;
        return null;
    }

    public void setLevelMap(LevelMap levelMap) {
        this.levelMap = levelMap;
    }
}
