package model.entity.word;

/**
 * Represents a conjunction word type in the game.
 * Currently, there is only one, "AND", which is integral to the rule's parsing system.
 */
public class ConjunctionType extends WordType {
    public ConjunctionType(int zIndex, String typeId, String spritePath) {
        super(zIndex, typeId, spritePath, PartOfSpeech.CONJUNCTION);
    }
}
