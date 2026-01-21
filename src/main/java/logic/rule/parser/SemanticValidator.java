package logic.rule.parser;


import model.entity.word.NounType;
import model.entity.word.PropertyType;
import model.rule.Rule;

import java.util.List;

public class SemanticValidator {

    public List<Rule> validate(List<Rule> rules) {
        return rules.stream()
                .filter(this::isSemanticallyValid)
                .toList();
    }

    private boolean isSemanticallyValid(Rule rule) {
        if(!rule.getVerb().acceptsNoun() && rule.getEffect() instanceof NounType) {
            return false;
        }
        if(!rule.getVerb().acceptsProperty() && rule.getEffect() instanceof PropertyType) {
            return false;
        }
        return true;
    }
}
