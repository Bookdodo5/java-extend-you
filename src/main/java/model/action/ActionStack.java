package model.action;

import java.util.Stack;

/**
 * Manages a stack of actions to enable undo and redo functionality.
 */
public class ActionStack {

    Stack<CompositeAction> undoStack;
    Stack<CompositeAction> redoStack;

    public ActionStack() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void newAction(CompositeAction compositeAction) {
        undoStack.push(compositeAction);
        redoStack.clear();
    }

    public void undo() {
        if(undoStack.isEmpty()) {
            return;
        }
        CompositeAction undoAction = undoStack.pop();
        undoAction.undo();
        redoStack.push(undoAction);
    }

    public void redo() {
        if(redoStack.isEmpty()) {
            return;
        }
        CompositeAction redoAction = redoStack.pop();
        redoAction.execute();
        undoStack.push(redoAction);
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
