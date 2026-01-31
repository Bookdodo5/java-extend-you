package application;

import javafx.scene.canvas.GraphicsContext;
import model.map.LevelLoader;
import model.map.LevelMap;
import state.*;

import java.util.HashMap;
import java.util.Map;

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
    private GameState currentState;

    private GameController() {
        stateMap = new HashMap<>();
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
    }

    public GameState getGameState(GameStateEnum stateEnum) {
        return stateMap.get(stateEnum);
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
    }

    public void update() {
        if(currentState != null) {
            currentState.update();
        }
    }

    public void render(GraphicsContext gc) {
        currentState.render(gc);
    }
}
