package com.tamaumi.takecare.common;

import lombok.Builder;

@Builder
public class ResponseTemplate {
    private final int statusCode;
    private final String message;
    private final Object data;
}
