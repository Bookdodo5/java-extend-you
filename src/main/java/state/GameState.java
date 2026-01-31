package state;

import javafx.scene.canvas.GraphicsContext;

public interface GameState {
    void onEnter();
    void onExit();
    void update();
    void render(GraphicsContext gc);
}
