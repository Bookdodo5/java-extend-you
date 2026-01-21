package logic.rule.parser;

import model.entity.Entity;
import model.entity.word.WordType;
import model.map.LevelMap;
import model.rule.Rule;
import model.rule.Ruleset;

import java.util.ArrayList;
import java.util.List;

public class RuleParser {
    private final TextScanner textScanner;
    private final PermutationGenerator permutationGenerator;
    private final SyntaxValidator syntaxValidator;
    private final SemanticValidator semanticValidator;
    private final RuleDeduplicator ruleDeduplicator;

    public RuleParser() {
        this.textScanner = new TextScanner();
        this.permutationGenerator = new PermutationGenerator();
        this.syntaxValidator = new SyntaxValidator();
        this.semanticValidator = new SemanticValidator();
        this.ruleDeduplicator = new RuleDeduplicator();
    }

    public List<Rule> parseRules(LevelMap levelMap) {
        List<List<List<Entity>>> textTiles = textScanner.scanText(levelMap);
        List<List<Entity>> ruleCandidates = permutationGenerator.generate(textTiles);
        List<Rule> rules = syntaxValidator.validate(ruleCandidates);
        List<Rule> validRules = semanticValidator.validate(rules);
        return ruleDeduplicator.deduplicate(validRules);
    }
}

