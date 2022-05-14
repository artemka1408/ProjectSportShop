package org.example.exception;

public class InvalidOffsetException extends Exception{
    public InvalidOffsetException() {
    }

    public InvalidOffsetException(String message) {
        super(message);
    }

    public InvalidOffsetException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOffsetException(Throwable cause) {
        super(cause);
    }

    public InvalidOffsetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
