package exception;

public class PlayerLeftException extends RuntimeException {
    public PlayerLeftException(String message) {
        super(message);
    }
}
