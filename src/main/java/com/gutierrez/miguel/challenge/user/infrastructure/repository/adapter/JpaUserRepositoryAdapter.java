package com.gutierrez.miguel.challenge.user.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.user.domain.exception.UserNotFoundException;
import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.domain.ports.UserRepositoryPort;
import com.gutierrez.miguel.challenge.user.infrastructure.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * JPA implementation of the UserRepositoryPort.
 * Handles the persistence of user data using JPA repositories.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository repository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        log.debug("Saving user: {}", user);
        var entity = mapper.toEntity(user);
        var savedEntity = repository.save(entity);
        log.debug("User saved successfully: {}", savedEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public User findById(UUID id) {
        log.debug("Finding user by id: {}", id);
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new UserNotFoundException("User with id %d not found", id);
                });
    }

    @Override
    public List<User> findAll() {
        log.debug("Finding all users");
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
} 