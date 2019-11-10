package com.cerbansouto.compucar.api;

public class UnauthorizedRequestException extends Exception {
    public UnauthorizedRequestException() {
    }

    public UnauthorizedRequestException(String message) {
        super(message);
    }

    public UnauthorizedRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
