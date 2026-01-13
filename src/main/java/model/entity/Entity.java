package logic.entity;

import java.util.UUID;

public class GameObject {

    UUID objectId;
    ObjectType objectType;
    int posX, posY;
    Direction direction;

    public GameObject(ObjectType objectType, int posX, int posY, Direction direction) {
        this.objectId = UUID.randomUUID();
        this.objectType = objectType;
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}