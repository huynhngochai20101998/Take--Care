package com.tamaumi.takecare.common.enums;

import lombok.Getter;

@Getter
public enum ResponseDefinition {
    SUCCESS(200, 1000, "Successfully"),
    REQUEST_FAILED(400, 4000, "Request Failed"),
    REGISTER_FAILED(400, 4000, "Registry Failed"),
    ;

    private final int statusCode;
    private final int devCode;
    private final String message;

    ResponseDefinition(int statusCode, int devCode, String message) {
        this.statusCode = statusCode;
        this.devCode = devCode;
        this.message = message;
    }
}
