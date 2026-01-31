package state;

import javafx.scene.canvas.GraphicsContext;

public class TitleState implements GameState {

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

    }

    /**
     *
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.rgb(100, 0, 0, 0.5));
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }
}
