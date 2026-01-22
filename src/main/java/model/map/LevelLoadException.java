package model.map;

public class LevelLoadException extends RuntimeException {
    public LevelLoadException(String message) {
        super(message);
    }

    public LevelLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LevelLoadException(Throwable cause) {
        super(cause);
    }
}
