package com.tamaumi.takecare.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseSetting {
    public static ResponseEntity<Object> response(int statusCode, String message) {
        return new ResponseEntity<>(
                new ResponseTemplate
                        .ResponseTemplateBuilder()
                        .statusCode(statusCode)
                        .message(message)
                        .build(),
                HttpStatus.OK
        );
    }

    public static ResponseEntity<Object> response(int statusCode, String message, Object data) {
        return new ResponseEntity<>(
                new ResponseTemplate
                        .ResponseTemplateBuilder()
                        .statusCode(statusCode)
                        .message(message)
                        .data(data)
                        .build(),
                HttpStatus.OK
        );
    }
}
