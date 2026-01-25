package model.rule;

import java.util.ArrayList;
import java.util.List;

public class Ruleset {
    private final ArrayList<Rule> rules;

    public Ruleset() {
        rules = new ArrayList<>();
    }

    public void setRules(List<Rule> ruleList) {
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