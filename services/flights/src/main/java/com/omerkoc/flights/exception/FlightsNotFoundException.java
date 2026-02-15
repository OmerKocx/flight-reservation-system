package com.omerkoc.flights.exception;

public class FlightsNotFoundException extends RuntimeException {
    public FlightsNotFoundException(String message) {
        super(message);
    }
}
