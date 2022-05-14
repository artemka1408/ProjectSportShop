package org.example.exception;

public class NotMakePurchaseException extends Exception {
    public NotMakePurchaseException() {
    }

    public NotMakePurchaseException(String message) {
        super(message);
    }

    public NotMakePurchaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMakePurchaseException(Throwable cause) {
        super(cause);
    }

    public NotMakePurchaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
