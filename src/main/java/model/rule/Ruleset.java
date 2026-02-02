package model.rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of rules.
 */
public class Ruleset {
    private final ArrayList<Rule> rules;

    public Ruleset() {
        rules = new ArrayList<>();
    }

    public void setRules(List<Rule> ruleList) {
        if(ruleList.equals(rules)) {
            return;
        }
        if(ruleList.size() >= rules.size()) {
            // TODO (SOUND) : play rule formation sound
        }
        if(ruleList.size() <= rules.size()) {
            // TODO (SOUND) : play rule breaking sound
        }
        reset();
        rules.addAll(ruleList);
    }

    public void reset() {
        rules.clear();
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }
}