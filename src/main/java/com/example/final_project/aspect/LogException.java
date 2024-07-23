package com.example.final_project.aspect;

public class LogException extends RuntimeException {
    /**
     * Default constructor for {@code LogException}.
     * Initializes the exception with no detail message or cause.
     */
    public LogException() {
    }

    /**
     * Constructs a {@code LogException} with the specified detail message.
     * The detail message is used to provide more information about the error.
     *
     * @param message the detail message, which is saved for later retrieval
     *                by the {@link #getMessage()} method
     */
    public LogException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code LogException} with the specified detail message and cause.
     * The detail message provides more information about the error,
     * and the cause allows to wrap another exception that caused this exception.
     *
     * @param message the detail message, which is saved for later retrieval
     *                by the {@link #getMessage()} method
     * @param cause   the cause of the exception, which is saved for later retrieval
     *                by the {@link #getCause()} method
     */
    public LogException(String message, Exception cause) {
        super(message, cause);
    }
}
