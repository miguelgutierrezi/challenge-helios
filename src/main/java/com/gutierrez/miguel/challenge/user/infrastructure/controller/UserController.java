package com.gutierrez.miguel.challenge.user.infrastructure.controller;

import com.gutierrez.miguel.challenge.user.application.usecases.CreateUserService;
import com.gutierrez.miguel.challenge.user.application.usecases.GetAllUsersService;
import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.infrastructure.dto.UserRequest;
import com.gutierrez.miguel.challenge.user.infrastructure.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing users.
 * This controller provides endpoints for creating and retrieving users,
 * handling user-related operations in the system.
 *
 * The controller follows REST principles and uses proper HTTP methods and status codes.
 * All endpoints are documented using OpenAPI/Swagger annotations for API documentation.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "APIs for managing users")
public class UserController {

    private final CreateUserService createUserService;
    private final GetAllUsersService getAllUsersService;

    /**
     * Creates a new user in the system.
     * This endpoint allows registering new users with their basic information.
     *
     * @param request The user creation request containing name and email
     * @return ResponseEntity containing the created user details
     */
    @Operation(
            summary = "Create user",
            description = "Creates a new user with the provided name and email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "409", description = "User with email already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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

    /**
     * Retrieves all users from the system.
     * This endpoint returns a list of all registered users.
     *
     * @return ResponseEntity containing a list of all users
     */
    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = getAllUsersService.getAllUsers();
        List<UserResponse> response = users.stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()))
                .toList();
        return ResponseEntity.ok(response);
    }
} 