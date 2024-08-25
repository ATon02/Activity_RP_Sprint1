package com.example.webfluxdemo.exception;

public class ItemNotCreatedException extends RuntimeException {
    public ItemNotCreatedException(String message) {
        super(message);
    }
}