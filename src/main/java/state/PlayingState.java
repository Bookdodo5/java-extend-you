package state;

import javafx.scene.canvas.GraphicsContext;
import application.GameController;
import logic.input.InputCommand;
import logic.level.LevelController;
import logic.input.InputUtility;
import model.entity.Entity;
import model.map.LevelMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayingState implements GameState {

    private final LevelController levelController;

    public void loadLevel(LevelMap levelMap) {
        levelController.setLevelMap(levelMap);
    }

    public PlayingState() {
        levelController = new LevelController();
    }

    /**
     *
     */
    @Override
    public void onEnter() {

    }

    /**
     *
     */
    @Override
    public void onExit() {

    }

    /**
     *
     */
    @Override
    public void update() {
        levelController.update();

        InputCommand playerInput = InputUtility.getTriggered();
        if (playerInput == InputCommand.MENU) {
            GameController.getInstance().setState(GameStateEnum.PAUSED);
        }
    }

    /**
     *
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.rgb(0, 0, 100, 0.5));
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        displayLevelMapInConsole(levelController.getLevelMap());
    }


    private void displayLevelMapInConsole(LevelMap levelMap) {
        StringBuilder frame = new StringBuilder("\033[H");
        for (int y = 0; y < levelMap.getHeight(); y++) {
            for (int x = 0; x < levelMap.getWidth(); x++) {
                String cellContent = formatCell(levelMap.getEntitiesAt(x, y));
                frame.append(String.format("%-3s", cellContent));
            }
            frame.append("\n");
        }
        System.out.printf(frame.toString());
    }

    private String formatCell(List<Entity> entities) {
        if (entities.isEmpty()) {
            return ".";
        }

        if (entities.size() == 1) {
            return Arrays.stream(entities.getFirst().getType().getTypeId().split("_")).toList().getLast().toUpperCase().charAt(0) + "";
        }

        return entities.stream()
                .map(entity -> Arrays.stream(entity.getType().getTypeId().split("_")).toList().getLast().toUpperCase().charAt(0) + "")
                .collect(Collectors.joining(""));
    }
}
