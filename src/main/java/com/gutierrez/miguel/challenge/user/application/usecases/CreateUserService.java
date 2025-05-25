package com.gutierrez.miguel.challenge.user.application.usecases;

import com.gutierrez.miguel.challenge.notification.application.usecases.UpdateNotificationPreferenceService;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.domain.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for creating users.
 * This service handles the business logic for creating new user accounts,
 * including validation and persistence of user data.
 *
 * The service follows the hexagonal architecture pattern, using ports for external dependencies
 * and focusing on the core business logic of user management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserService {

    private final UserRepositoryPort userRepository;
    private final UpdateNotificationPreferenceService notificationPreferenceService;

    /**
     * Creates a new user in the system.
     * This method handles the complete user creation process, including:
     * 1. Generating a unique identifier for the user
     * 2. Creating a new user with the provided details
     * 3. Persisting the user data
     * 4. Logging the operation at appropriate levels
     *
     * @param name The name of the user
     * @param email The email address of the user
     * @return The created user
     * @throws RuntimeException if there is an error creating the user
     */
    public User create(String name, String email) {
        log.info("Creating new user with name: {}, email: {}", name, email);

        try {
            User user = User.builder()
                    .id(UUID.randomUUID())
                    .name(name)
                    .email(email)
                    .build();

            User savedUser = userRepository.save(user);
            log.info("Successfully created user with ID: {}", savedUser.getId());

            for (NotificationCategory category : NotificationCategory.values()) {
                notificationPreferenceService.updatePreference(savedUser.getId(), category, true);
            }

            return savedUser;
        } catch (Exception e) {
            log.error("Error creating user with name: {}, email: {}, error: {}",
                    name, email, e.getMessage(), e);
            throw e;
        }
    }
} 