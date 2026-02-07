package application;

import javafx.scene.canvas.GraphicsContext;
import model.map.LevelLoader;
import model.map.LevelMap;
import state.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A singleton class that manages the overall state of the game.
 * <ul>
 *     <li>Game states and transitions</li>
 *     <li>Communication between logic and graphic</li>
 *     <li>Game progressions</li>
 *     <li>etc. (there can be more)</li>
 * </ul>
 */
public class GameController {
    private static GameController instance;
    private final Map<GameStateEnum, GameState> stateMap;
    private final Set<String> completedLevels;
    private GameState currentState;
    private boolean isCurrentLevelWin;
    private String currentLevelFilePath;
    private LevelMap currentLevelMapPrototype;

    private GameController() {
        stateMap = new HashMap<>();
        completedLevels = new HashSet<>();
        currentLevelFilePath = null;
        stateMap.put(GameStateEnum.PLAYING, new PlayingState());
        stateMap.put(GameStateEnum.MAP, new MapState());
        stateMap.put(GameStateEnum.TITLE, new TitleState());
        stateMap.put(GameStateEnum.PAUSED, new PauseState());
    }

    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void setState(GameStateEnum newState) {
        if(currentState != null) {
            currentState.onExit();
        }
        currentState = stateMap.get(newState);
        if(currentState != null) {
            currentState.onEnter();
        }
        if(newState != GameStateEnum.PAUSED && newState != GameStateEnum.PLAYING) {
            resetCurrentLevel();
        }
    }

    public GameState getGameState(GameStateEnum stateEnum) {
        return stateMap.get(stateEnum);
    }

    private void resetCurrentLevel() {
        currentLevelFilePath = null;
        isCurrentLevelWin = false;
        currentLevelMapPrototype = null;
    }

    public void playLevel(String levelFilePath) {
        PlayingState playingState = (PlayingState) getGameState(GameStateEnum.PLAYING);
        LevelMap levelMap = LevelLoader.loadLevel(levelFilePath);
        if(levelMap == null) {
            System.err.println("Failed to load level: " + levelFilePath);
            return;
        }
        playingState.loadLevel(levelMap);
        setState(GameStateEnum.PLAYING);
        currentLevelFilePath = levelFilePath;
        currentLevelMapPrototype = levelMap;
    }

    public boolean isLevelCompleted(String levelFilePath) {
        return completedLevels.contains(levelFilePath);
    }

    public void update() {
        if(currentState != null) {
            currentState.update();
        }
    }

    public void setCurrentLevelWin(boolean isWin) {
        this.isCurrentLevelWin = isWin;
    }

    public boolean processWin() {
        if(isCurrentLevelWin) {
            completedLevels.add(currentLevelFilePath);
            setState(GameStateEnum.MAP);
            resetCurrentLevel();
            //TODO (SOUND) : play win sound;

            return true;
        }
        return false;
    }

    public LevelMap getCurrentLevelMapPrototype() {
        return currentLevelMapPrototype;
    }

    public void render(GraphicsContext gc) {
        currentState.render(gc);
    }
}
