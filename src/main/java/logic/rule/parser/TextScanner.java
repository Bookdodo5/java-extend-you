package logic.rule.parser;

import model.entity.Entity;
import model.entity.word.WordType;
import model.map.LevelMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Scans a LevelMap for sequences of word entities arranged in lines (horizontally or vertically) which have a chance to be a rule.
 */
public class TextScanner {

    public List<List<List<Entity>>> scanText(LevelMap levelMap) {
        List<List<List<Entity>>> results = new ArrayList<>();

        for(int y = 0; y < levelMap.getHeight(); y++) {
            scanLine(levelMap, y, levelMap.getWidth(), true, results);
        }

        for(int x = 0; x < levelMap.getWidth(); x++) {
            scanLine(levelMap, x, levelMap.getHeight(), false, results);
        }

        return results;
    }

    private void scanLine(LevelMap levelMap, int fixedDim, int maxDim, boolean isScanAcrossX, List<List<List<Entity>>> results ) {
        List<List<Entity>> currentLine = new ArrayList<>();
        for(int i = 0; i < maxDim; i++) {
            int x = isScanAcrossX ? i : fixedDim;
            int y = isScanAcrossX ? fixedDim : i;

            List<Entity> words = levelMap.getEntitiesAt(x, y).stream()
                    .filter((Entity e) -> e.getType() instanceof WordType)
                    .toList();

            if(!words.isEmpty()) {
                currentLine.add(words);
                continue;
            }
            if(currentLine.size() >= 3) results.add(currentLine);
            currentLine = new ArrayList<>();
        }
        if(currentLine.size() >= 3) results.add(currentLine);
    }
}
