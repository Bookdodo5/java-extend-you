package model.action;

/**
 * Represents an action that can be executed and undone. This will be stored for undo/redo functionality.
 */
public interface Action {
    void execute();
    void undo();
}