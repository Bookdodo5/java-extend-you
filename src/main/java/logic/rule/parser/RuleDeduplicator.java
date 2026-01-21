package logic.rule.parser;

import model.rule.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleDeduplicator {

    public List<Rule> deduplicate(List<Rule> rules) {
        Set<String> seen = new HashSet<>();
        return rules.stream()
                .filter(rule -> checkSeen(rule, seen))
                .toList();
    }

    private boolean checkSeen(Rule rule, Set<String> seen) {
        String ruleSignature = rule.getEntitySignature();
        if(seen.contains(ruleSignature)) {
            return false;
        } else {
            seen.add(ruleSignature);
            return true;
        }
    }
}











