package com.gutierrez.miguel.challenge.user.application.usecases;

import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.domain.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for creating new user accounts.
 * This service handles the business logic for creating and persisting new user records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserService {

    private final UserRepositoryPort repository;

    /**
     * Creates a new user account with the provided information.
     * Generates a new UUID for the user and persists the record.
     *
     * @param name The full name of the user
     * @param email The email address of the user
     * @return The created User entity with its generated ID
     */
    public User create(String name, String email) {
        log.info("Creating new user account - name: {}, email: {}", name, email);
        
        try {
            UUID userId = UUID.randomUUID();
            log.debug("Generated new UUID for user: {}", userId);
            
            User user = User.builder()
                    .id(userId)
                    .name(name)
                    .email(email)
                    .build();
            log.debug("User object created with ID: {}", user.getId());
            
            User savedUser = repository.save(user);
            log.info("Successfully created user account - ID: {}, name: {}, email: {}", 
                    savedUser.getId(), savedUser.getName(), savedUser.getEmail());
            
            return savedUser;
        } catch (Exception e) {
            log.error("Error creating user account - name: {}, email: {}, error: {}", 
                    name, email, e.getMessage(), e);
            throw e;
        }
    }
} 