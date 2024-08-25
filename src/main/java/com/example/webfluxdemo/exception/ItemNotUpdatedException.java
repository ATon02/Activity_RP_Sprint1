package com.example.webfluxdemo.exception;

public class ItemNotUpdatedException extends RuntimeException {
    public ItemNotUpdatedException(String message) {
        super(message);
    }
}