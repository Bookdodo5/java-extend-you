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
        if (playerInput == InputCommand.MENU) {
            GameController.getInstance().setState(GameStateEnum.PLAYING);
        }
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
