package javabackendfelvfeladat.exception;

public class ImageZipCreationException extends RuntimeException {
    public ImageZipCreationException(String message) {
        super(message);
    }

    public ImageZipCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
