package model.action;

import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.map.LevelMap;
import model.particle.Particle;
import model.particle.ParticleType;
import state.PlayingState;
import utils.ImageUtils;

/**
 * An action that destroys an entity from the level map.
 */
public class DestroyAction implements Action {

    private final LevelMap levelMap;
    private final Entity entity;
    private final int posX;
    private final int posY;

    public DestroyAction(LevelMap levelMap, Entity entity) {
        this.levelMap = levelMap;
        this.entity = entity;
        this.posX = levelMap.getEntityX(entity);
        this.posY = levelMap.getEntityY(entity);
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void execute() {
        levelMap.removeEntity(entity);
    }

    @Override
    public void undo() {
        levelMap.setEntityPosition(entity, posX, posY);
    }

    public void addParticle(PlayingState playingState) {
        for (int i = 0; i < 4; i++) {
            playingState.addParticle(new Particle(
                    posX + (Math.random() - 0.5) / 4.0,
                    posY + (Math.random() - 0.5) / 4.0,
                    (Math.random() - 0.5) / 1000.0,
                    (Math.random() - 0.5) / 1000.0,
                    ParticleType.DESTROY,
                    ImageUtils.averageColor(entity.getType().getSpriteSheet())
            ));
        }
    }
}