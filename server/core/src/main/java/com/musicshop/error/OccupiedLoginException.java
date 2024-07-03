package com.musicshop.error;

public class OccupiedLoginException extends RuntimeException {
    public OccupiedLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
