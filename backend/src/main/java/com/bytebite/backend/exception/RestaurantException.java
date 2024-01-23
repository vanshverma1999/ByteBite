package com.bytebite.backend.exception;

public class RestaurantException extends Exception {
    private static final long serialVersionUID = 1L;

    public RestaurantException(String message) {
        super(message);
    }
}