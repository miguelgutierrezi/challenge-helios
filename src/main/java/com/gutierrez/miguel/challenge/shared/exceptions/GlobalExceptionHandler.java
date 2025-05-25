package com.gutierrez.miguel.challenge.shared.exceptions;

import com.gutierrez.miguel.challenge.cardholder.domain.exception.CardholderNotFoundException;
import com.gutierrez.miguel.challenge.cardholder.domain.exception.InsufficientBalanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({CardholderNotFoundException.class})
    public ResponseEntity<String> handleNotFound(Exception ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({InsufficientBalanceException.class})
    public ResponseEntity<String> handleUnprocessableEntity(Exception ex) {
        log.error("Unprocessable entity: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
