package exception;

/**
 * Exception represents operations not supposed to be acted.
 *
 * <p>
 *     This exception is used to maintain game mechanism and procedures, to be thrown when function called at
 *     a time it's not supposed to be called.
 *     For example, sending player the third hole card.
 * </p>
 *
 * <p>
 *     This is a RuntimeException, occur usually with programming mistakes.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class IllegalOperationException extends RuntimeException {
    /**
     * Instancing exception using appointed message
     *
     * <p>Do describe situations in detail for logging and debugging.</p>
     *
     * @param message Exception noting message
     */
    public IllegalOperationException(String message) {
        super(message);
    }
}
