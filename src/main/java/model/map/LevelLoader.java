package model.map;

import model.entity.Direction;
import model.entity.Entity;
import model.entity.EntityType;
import model.entity.TypeRegistry;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Utility class for loading maps from CSV files.
 * <p>
 * The CSV file format is as follows:
 * <ul>
 * <li>First line: width,height</li>
 * <li>Each following line: comma-separated cells (one row per line)</li>
 * <li>Use '+' to stack multiple entities in a single tile (e.g. "java+flag")</li>
 * <li>Use '.' to represent an empty tile</li>
 * </ul>
 *
 * <p>Example (level1.csv):</p>
 * 5,3
 * wire+water,water,text_java,is,you
 * java,.,text_flag,is,win
 * .,.,flag,.,lava
 */

public class LevelLoader {

    private LevelLoader() {
    }

    /**
     * Loads a level map from a CSV file.
     *
     * @param fileName the name of the CSV file to load
     * @return the loaded LevelMap, or null if loading failed
     */
    public static LevelMap loadLevel(String fileName) {

        InputStream inputStream = LevelLoader.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            System.err.println("File not found: " + fileName);
            return null;
        }

        try (Scanner myReader = new Scanner(inputStream)) {
            if (!myReader.hasNextLine()) return null;
            String[] mapSize = myReader.nextLine().trim().split(",");
            int width = Integer.parseInt(mapSize[0]);
            int height = Integer.parseInt(mapSize[1]);

            LevelMap levelMap = new LevelMap(width, height);

            for (int i = 0; i < height; i++) {
                if (!myReader.hasNextLine()) break;
                String[] cells = myReader.nextLine().trim().split(",");

                for (int j = 0; j < Math.min(width, cells.length); j++) {
                    String cellData = cells[j].trim();
                    if (cellData.isBlank()) continue;
                    String[] entityIds = cellData.split("\\+");

                    for (String entityId : entityIds) {
                        String[] parts = entityId.trim().split("-");

                        EntityType entityType = TypeRegistry.getType(parts[0]);
                        if (entityType == null) continue;
                        Entity newEntity = new Entity(entityType);

                        Direction facing = Direction.DOWN;
                        if (parts.length == 2) {
                            facing = Direction.valueOf(parts[1].toUpperCase());
                        }
                        newEntity.setDirection(facing);

                        levelMap.setPosition(newEntity, j, i);
                    }
                }
            }
            return levelMap;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
