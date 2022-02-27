package com.tamaumi.takecare.exception;

public class RegistrationFailedException extends RuntimeException{
    private final Integer DevCode;

    public RegistrationFailedException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
