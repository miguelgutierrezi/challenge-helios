package com.gutierrez.miguel.challenge.user.infrastructure.controller;

import com.gutierrez.miguel.challenge.user.application.usecases.CreateUserService;
import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.infrastructure.dto.UserRequest;
import com.gutierrez.miguel.challenge.user.infrastructure.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing users in the system.
 * Provides endpoints for creating and managing user accounts.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final CreateUserService createUserService;

    /**
     * Creates a new user account.
     *
     * @param request The user creation request containing name and email
     * @return ResponseEntity containing the created user information
     */
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user account with the provided name and email"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters"
        )
    })
    @PostMapping
    public ResponseEntity<UserResponse> create(
            @Parameter(description = "User creation details", required = true)
            @RequestBody UserRequest request) {
        User user = createUserService.create(request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                )
        );
    }
} 