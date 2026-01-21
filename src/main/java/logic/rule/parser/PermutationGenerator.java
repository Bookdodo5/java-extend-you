package logic.rule.parser;

import model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class PermutationGenerator {

    private void generateRecursive(List<List<Entity>> result, List<List<Entity>> processingLine, List<Entity> current) {
        if(processingLine.size() == current.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        int nextIndex = current.size();
        List<Entity> nextEntities = processingLine.get(nextIndex);
        for(Entity nextEntity : nextEntities) {
            current.add(nextEntity);
            generateRecursive(result, processingLine, current);
            current.removeLast();
        }
    }

    public List<List<Entity>> generate(List<List<List<Entity>>> textTiles) {
        List<List<Entity>> result = new ArrayList<>();

        for(List<List<Entity>> line : textTiles) {
            generateRecursive(result, line, new ArrayList<>());
        }

        return result;
    }
}
