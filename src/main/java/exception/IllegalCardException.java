package exception;

/**
 * Exception represents invalid poker card
 *
 * <p>
 *     This exception is used in poker card instance creating, to be thrown when creating an invalid card.
 *     For example, suited JOKER.
 * </p>
 *
 * <p>
 *     This is a RuntimeException, occur usually because of programming mistakes or data corruption.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class IllegalCardException extends RuntimeException {

  /**
   * Instancing exception using appointed message
   *
   * <p>Do describe reasons for card invalidation. Such as Suited Joker, Value out of bounds.</p>
   *
   * @param message Exception noting message
   */
  public IllegalCardException(String message) {
    super(message);
  }
}
