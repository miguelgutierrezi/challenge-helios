package com.gutierrez.miguel.challenge.user.domain.exception;

/**
 * Exception thrown when a requested user is not found in the system.
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
} 