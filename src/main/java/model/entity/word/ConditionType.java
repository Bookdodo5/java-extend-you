package model.entity.word;

/**
 * Represents a condition type word in the game.
 * A condition is like a preposition inside the rule, which will only make the rule apply if the condition is met.
 * (ON, FACING, etc.)
 */
public class ConditionType extends WordType {
    public ConditionType(int zIndex, String typeId, String spritePath) {
        super(zIndex, typeId, spritePath, PartOfSpeech.CONDITION);
    }
}
