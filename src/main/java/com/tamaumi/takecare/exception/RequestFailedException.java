package com.tamaumi.takecare.exception;

import lombok.Getter;

@Getter
public class RequestFailedException extends RuntimeException{
    private final Integer DevCode;

    public RequestFailedException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
