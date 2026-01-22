package logic.controller;

import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.map.LevelMap;
import model.rule.Rule;

public interface WordInteraction {
    // --- Rule Lifecycle ---
    default void onRuleCreated(Rule ctx) {
    }

    default void onRuleDeleted(Rule ctx) {
    }

    // --- Movement & Collision ---
    default boolean onAttemptMove(Entity entity, Direction direction, LevelMap levelMap) {
        return true;
    }

    default void onPreMove(Entity entity, Direction direction, LevelMap levelMap) {
    }

    default void onPostMove(Entity entity, Direction direction, LevelMap levelMap) {
    }

    default void onOverlap(Entity mover, Entity staticEntity) {
    }

    // --- Turn Lifecycle ---
    default void onTurnStart(LevelMap levelMap) {
    }

    default void onTurnEnd(LevelMap levelMap) {
    }

    // --- Entity Lifecycle ---
    default void onCreated(Entity e) {
    }

    default void onDestroyed(Entity e) {
    }

    default void onTransform(Entity e, EntityType targetType) {
    }

    // --- Visuals ---
    default void onUpdate(Entity e, float dt) {
    }
}
