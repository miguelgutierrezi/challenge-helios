package com.gutierrez.miguel.challenge.user.application.usecases;

import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.domain.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for retrieving all users from the system.
 * This service handles the business logic for fetching and returning all user records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllUsersService {

    private final UserRepositoryPort repository;

    /**
     * Retrieves all users from the system.
     * Logs the operation and returns a list of all users.
     *
     * @return List of all users in the system
     */
    public List<User> getAllUsers() {
        log.info("Retrieving all users");
        try {
            List<User> users = repository.findAll();
            log.info("Successfully retrieved {} users", users.size());
            return users;
        } catch (Exception e) {
            log.error("Error retrieving users: {}", e.getMessage(), e);
            throw e;
        }
    }
}
