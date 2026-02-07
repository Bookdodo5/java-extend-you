package model.action;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import model.entity.Direction;
import model.entity.Entity;
import model.map.LevelMap;
import model.particle.Particle;
import model.particle.ParticleType;
import state.PlayingState;
import utils.ImageUtils;

import java.awt.*;

/**
 * An action that moves an entity in a specified direction on the level map.
 */
public class MoveAction implements Action {

    private final LevelMap levelMap;
    private final Entity entity;
    private final int endX, endY;
    private int startX, startY;
    private final Direction endDirection;
    private Direction startDirection;

    public MoveAction(LevelMap levelMap, Entity entity, Direction direction) {
        this.levelMap = levelMap;
        this.entity = entity;
        this.startDirection = entity.getDirection();
        this.endDirection = direction;
        this.endX = levelMap.getEntityX(entity) + direction.dx;
        this.endY = levelMap.getEntityY(entity) + direction.dy;
        startX = levelMap.getEntityX(entity);
        startY = levelMap.getEntityY(entity);
    }

    @Override
    public void execute() {
        startX = levelMap.getEntityX(entity);
        startY = levelMap.getEntityY(entity);
        startDirection = entity.getDirection();
        levelMap.setEntityPosition(entity, endX, endY);
        entity.setDirection(endDirection);
    }

    @Override
    public void undo() {
        levelMap.setEntityPosition(entity, startX, startY);
        entity.setDirection(startDirection);
    }

    public void addParticle(PlayingState playingState) {
        playingState.addParticle(new Particle(
                startX + (Math.random() - 0.5) / 2.0,
                startY + (Math.random() - 0.5) / 2.0,
                (startX - endX) / 500.0, (startY - endY) / 500.0,
                ParticleType.PUFF,
                ImageUtils.averageColor(entity.getType().getSpriteSheet())
        ));
    }
}
