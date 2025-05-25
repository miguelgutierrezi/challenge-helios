package com.gutierrez.miguel.challenge.cardholder.domain.exception;

public class CardholderNotFoundException extends RuntimeException {
    public CardholderNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
