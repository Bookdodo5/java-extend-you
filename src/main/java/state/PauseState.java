package state;

import application.GameController;
import javafx.scene.canvas.GraphicsContext;
import logic.input.InputCommand;
import logic.input.InputUtility;

public class PauseState implements GameState {
    /**
     *
     */
    @Override
    public void onEnter() {
        // TODO (SOUND) : play menu enter sound
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
        InputCommand playerInput = InputUtility.getTriggered();
        switch (playerInput) {
            case MENU -> GameController.getInstance().setState(GameStateEnum.PLAYING);
            case MOVE_UP -> handleMoveUp();
            case MOVE_DOWN -> handleMoveDown();
            case TRIGGER -> handleTrigger();
        }
    }

    private void handleMoveUp() {
        // TODO (SOUND) : play menu move sound
    }

    private void handleMoveDown() {
        // TODO (SOUND) : play menu move sound
    }

    private void handleTrigger() {
        // TODO (SOUND) : play menu select sound
    }

    /**
     *
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.rgb(0, 100, 0, 0.5));
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }
}
