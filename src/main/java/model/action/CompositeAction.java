package model.action;

import java.util.ArrayList;
import java.util.List;

/**
 * A composite action that aggregates multiple actions into one.
 */
public class CompositeAction implements Action {

    private final List<Action> actions;

    public CompositeAction() {
        this.actions = new ArrayList<>();
    }

    public void add(Action action) {
        actions.add(action);
    }

    public void combine(CompositeAction other) {
        actions.addAll(other.actions);
    }

    public int size() {
        return actions.size();
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public void execute() {
        for (Action action : actions) {
            action.execute();
        }
    }

    @Override
    public void undo() {
        for (int i = actions.size() - 1; i >= 0; i--) {
            actions.get(i).undo();
        }
    }
}
