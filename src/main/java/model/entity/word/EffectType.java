package model.entity.word;

/**
 * Abstract class representing an effect type in the game.
 * An effect type is any type of word that can follows verb. This includes nouns and properties.
 */
public abstract class EffectType extends WordType {
    public EffectType(int zIndex, String typeId, String spritePath, PartOfSpeech partOfSpeech) {
        super(zIndex, typeId, spritePath, partOfSpeech);
    }
}
