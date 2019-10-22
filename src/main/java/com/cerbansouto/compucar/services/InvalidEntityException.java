package com.cerbansouto.compucar.services;

public class InvalidEntityException extends Exception {

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Exception inner) {
        super(message, inner);
    }
}
