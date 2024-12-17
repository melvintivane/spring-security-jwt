package tech.melvin.springsecurity6.controller.exception;

public class GlobalException extends RuntimeException {
    private final int statusCode;

    public GlobalException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
