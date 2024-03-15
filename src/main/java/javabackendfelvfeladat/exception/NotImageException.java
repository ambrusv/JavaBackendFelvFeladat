package javabackendfelvfeladat.exception;

import lombok.Getter;

@Getter
public class NotImageException extends RuntimeException {

    private final String errorMessage;
    private final String field;

    public NotImageException(String errorMessage, String field) {
        this.errorMessage = errorMessage;
        this.field = field;
    }
}

