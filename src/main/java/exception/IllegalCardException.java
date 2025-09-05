package exception;

public class IllegalCardException extends RuntimeException {
  public IllegalCardException(String message) {
    super(message);
  }
}
