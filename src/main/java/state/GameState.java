package state;

import javafx.scene.canvas.GraphicsContext;

public interface GameState {
    void onEnter(GameStateEnum previousState);
    void onExit();
    void update();
    void render(GraphicsContext gc);
}
