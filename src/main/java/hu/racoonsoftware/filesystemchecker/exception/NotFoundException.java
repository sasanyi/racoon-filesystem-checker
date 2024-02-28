package hu.racoonsoftware.filesystemchecker.exception;

/**
 * This class base of all specific Not Found (404) error. This caught by global exception handler.
 * @see hu.racoonsoftware.filesystemchecker.controller.GlobalExceptionHandler
 */
public class NotFoundException extends RuntimeException {
    /**
     * @param message The message of specific error
     */
    public NotFoundException(String message) {
        super(message);
    }
}
