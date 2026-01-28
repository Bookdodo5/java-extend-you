package model.entity.word;

/**
 * Represents a verb word type in the game.
 * Verbs can accept nouns or properties as an effect, and only nouns as a subject. (IS, HAS, EXTEND)
 */
public class VerbType extends WordType{

    private final boolean acceptsNoun;
    private final boolean acceptsProperty;

    public VerbType(int zIndex, String typeId, String spritePath, boolean acceptsNoun, boolean acceptsProperty) {
        super(zIndex, typeId, spritePath, PartOfSpeech.VERB);
        this.acceptsNoun = acceptsNoun;
        this.acceptsProperty = acceptsProperty;
    }

    public boolean acceptsNoun() {
        return acceptsNoun;
    }

    public boolean acceptsProperty() {
        return acceptsProperty;
    }
}
