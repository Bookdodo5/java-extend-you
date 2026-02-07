package model.entity.word;

import model.entity.EntityType;

/**
 * Represents a noun word type in a game. A noun refers to an entity type, which when formed into a rule, the rule applies to that entity type.
 * (TEXT_JAVA, TEXT_FLAG, TEXT_LAVA, TEXT_WATER, etc.)
 */
public class NounType extends EffectType {
    EntityType referencedType;

    public NounType(int zIndex, String typeId, EntityType referencedType) {
        super(zIndex, typeId, PartOfSpeech.NOUN);
        this.referencedType = referencedType;
    }

    public EntityType getReferencedType() {
        return referencedType;
    }
}
