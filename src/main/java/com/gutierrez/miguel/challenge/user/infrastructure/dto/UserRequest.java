package com.gutierrez.miguel.challenge.user.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for user creation requests.
 * Contains the necessary information to create a new user.
 */
@Schema(description = "Request object for creating users")
public record UserRequest(
    @Schema(description = "Full name of the user", example = "John Doe")
    String name,
    
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    String email
) {
    public UserRequest {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
    }
} 