package model.entity.word;

/**
 * Represents a property word type in the game.
 * Properties are what defines the behavior of entities. (YOU, MOVE, STOP, PUSH, etc.)
 */
public class PropertyType extends EffectType {
    public PropertyType(int zIndex, String typeId) {
        super(zIndex, typeId, PartOfSpeech.PROPERTY);
    }
}
