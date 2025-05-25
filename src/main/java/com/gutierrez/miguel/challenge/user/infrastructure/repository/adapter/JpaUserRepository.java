package com.gutierrez.miguel.challenge.user.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.user.infrastructure.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA repository interface for User entities.
 * Provides basic CRUD operations for user data.
 */
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
} 