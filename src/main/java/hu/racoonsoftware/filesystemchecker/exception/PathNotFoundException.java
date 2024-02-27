package hu.racoonsoftware.filesystemchecker.exception;

/**
 * This exception class represent a specific {@link hu.racoonsoftware.filesystemchecker.exception.NotFoundException}
 */
public class PathNotFoundException extends NotFoundException {
    public PathNotFoundException() {
        super("Invalid path");
    }
}
