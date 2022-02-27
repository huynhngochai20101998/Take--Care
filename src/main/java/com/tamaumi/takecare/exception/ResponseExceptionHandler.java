package com.tamaumi.takecare.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseExceptionHandler {

    private final static Logger LOGGER = LogManager.getLogger(ResponseExceptionHandler.class);

    private ResponseEntity<Object> responseException(
            ResponseException exception,
            HttpStatus httpStatus
    ) {
        return new ResponseEntity<>(exception, httpStatus);
    }

    @ExceptionHandler(value = RequestFailedException.class)
    public ResponseEntity<Object> handleException(RequestFailedException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ResponseException responseException = new ResponseException(
                httpStatus.value(),
                exception.getDevCode(),
                exception.getMessage()
        );

        return responseException(responseException, httpStatus);
    }
}
