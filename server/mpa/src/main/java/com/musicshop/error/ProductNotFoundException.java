package com.musicshop.error;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}