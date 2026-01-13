package logic.entity;

public class ObjectTextType extends ObjectType {
    PartOfSpeech partOfSpeech;
    ObjectType referencedType;

    public ObjectTextType(int zIndex, String typeId, String spritePath, PartOfSpeech partOfSpeech) {
        this(zIndex, typeId, spritePath, partOfSpeech, null);
    }

    public ObjectTextType(int zIndex, String typeId, String spritePath, PartOfSpeech partOfSpeech, ObjectType referencedType) {
        super(zIndex, typeId, spritePath, AnimationStyle.WOBBLE);
        this.partOfSpeech = partOfSpeech;
        this.referencedType = referencedType;
    }

    @Override
    public boolean isText() {
        return true;
    }

    public ObjectType getReferencedType() {
        return referencedType;
    }
}
