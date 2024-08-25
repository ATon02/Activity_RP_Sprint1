package com.example.webfluxdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public Mono<ResponseEntity<String>> handleItemNotFoundException(ItemNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler(ItemNotCreatedException.class)
    public Mono<ResponseEntity<String>> handleItemNotCreatedException(ItemNotCreatedException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(ItemNotUpdatedException.class)
    public Mono<ResponseEntity<String>> handleItemNotUpdatedException(ItemNotUpdatedException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(NotValidFieldException.class)
    public Mono<ResponseEntity<String>> handleEmptyFieldException(NotValidFieldException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }
}