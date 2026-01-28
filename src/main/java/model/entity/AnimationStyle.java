package model.entity;

/**
 * Enum representing different animation styles for entities.
 * <ul>
 *     <li>WOBBLE: An animation style where the entity rotates between 3 frames of its sprite.</li>
 *     <li>TILED: An animation style where the entity changes sprite depending on how many of the same entity type is above, below, left, or right of it.</li>
 *     <li>DIRECTIONAL: An animation style where the entity's sprite changes depending on its facing direction.</li>
 * </ul>
 */
public enum AnimationStyle {
    WOBBLE, TILED, DIRECTIONAL
}
