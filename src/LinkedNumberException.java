/**
 * Represents an exception specific to {@code LinkedNumber} operations.
 * This exception is thrown to indicate that an operation on a LinkedNumber
 * has encountered an issue that prevents it from completing successfully.
 */
public class LinkedNumberException extends RuntimeException {
    /**
     * Constructs a new {@code LinkedNumberException} with the specified detail message.
     * The message provides more information about the reason the exception was thrown.
     *
     * @param msg the detail message. The detail message is saved for later retrieval
     *            by the {@link Throwable#getMessage()} method.
     */
    public LinkedNumberException(String msg) {
        super(msg);
    }
}
