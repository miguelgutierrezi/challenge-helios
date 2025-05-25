package com.gutierrez.miguel.challenge.cardholder.domain.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message, Object... args) {
        super(String.format(message, args));
    }
}
