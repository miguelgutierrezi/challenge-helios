package com.gutierrez.miguel.challenge.user.domain.ports;

import com.gutierrez.miguel.challenge.user.domain.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Port interface for user repository operations.
 * Defines the contract for user persistence operations.
 */
public interface UserRepositoryPort {
    /**
     * Saves a user to the repository.
     *
     * @param user The user to save
     * @return The saved user with any generated fields
     */
    User save(User user);

    /**
     * Finds a user by their ID.
     *
     * @param id The UUID of the user to find
     * @return The found user
     * @throws UserNotFoundException if no user exists with the given ID
     */
    User findById(UUID id);

    /**
     * Retrieves all users from the repository.
     *
     * @return List of all users
     */
    List<User> findAll();
} 