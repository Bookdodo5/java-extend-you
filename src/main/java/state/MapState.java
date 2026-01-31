package state;

import javafx.scene.canvas.GraphicsContext;

public class MapState implements GameState {
    /**
     *
     */
    @Override
    public void onEnter() {
        // Code here happens when entering the level selector (world map)
    }

    /**
     *
     */
    @Override
    public void onExit() {
        // Code here happens when exiting the level selector
    }

    /**
     *
     */
    @Override
    public void update() {
        // Code here is called every frame while in the level selector

        /*
            Handle input like this:

            InputCommand input = InputUtility.getTriggered();
            switch (input) {
                case NONE -> {
                    return;
                }
                case MOVE_UP -> {
                    // Move the cursor up
                }
                case MOVE_DOWN -> {
                    // Move the cursor down
                }
                case SELECT -> {
                    // Select the current level at cursor
                    // Call GameController.playLevel(levelPathString);
                }
            }
         */
    }

    /**
     *
     */
    @Override
    public void render(GraphicsContext gc) {
        // Paints the level selector map
        // Consider the current cursor position

        gc.setFill(javafx.scene.paint.Color.rgb(0, 100, 100, 0.5));
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }
}
