package com.gutierrez.miguel.challenge.user.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * Data Transfer Object for user responses.
 * Contains the user information to be returned to clients.
 */
@Schema(description = "Response object containing user information")
public record UserResponse(
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id,
    
    @Schema(description = "Full name of the user", example = "John Doe")
    String name,
    
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    String email
) {
} 