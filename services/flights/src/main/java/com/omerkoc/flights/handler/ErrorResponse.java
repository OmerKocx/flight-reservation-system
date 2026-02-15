package com.omerkoc.flights.handler;

import java.util.Map;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        String errorCode,
        String path,
        int status,
        long timestamp,
        String traceId,
        Map<String, String> validationErrors) {
}