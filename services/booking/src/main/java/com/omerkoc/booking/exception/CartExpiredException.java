package com.omerkoc.booking.exception;

public class CartExpiredException extends RuntimeException {
    public CartExpiredException(String message) {
        super(message);
    }
}
