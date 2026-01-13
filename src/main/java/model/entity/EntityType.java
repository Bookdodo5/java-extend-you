package logic.entity;

public class ObjectType {

    String spritePath;
    AnimationStyle animationStyle;
    int ZIndex;

    public ObjectType(int ZIndex, String typeId, String spritePath, AnimationStyle animationStyle) {
        this.typeId = typeId;
        this.spritePath = spritePath;
        this.animationStyle = animationStyle;
        this.ZIndex = ZIndex;
    }

    String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public AnimationStyle getAnimationStyle() {
        return animationStyle;
    }

    public void setAnimationStyle(AnimationStyle animationStyle) {
        this.animationStyle = animationStyle;
    }

    public int getZIndex() {
        return ZIndex;
    }

    public void setZIndex(int ZIndex) {
        this.ZIndex = ZIndex;
    }

    public boolean isText() {
        return false;
    }

    public boolean equals(String typeId) {
        return this.typeId.equals(typeId);
    }
}
