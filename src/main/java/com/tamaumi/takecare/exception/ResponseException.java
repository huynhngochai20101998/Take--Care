package com.tamaumi.takecare.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseException {

    private int statusCode;
    private int devCode;
    private String message;

    public ResponseException(int statusCode, int devCode, String message) {
        this.statusCode = statusCode;
        this.devCode = devCode;
        this.message = message;
    }
}
