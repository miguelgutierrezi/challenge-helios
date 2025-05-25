package com.gutierrez.miguel.challenge.user.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.infrastructure.repository.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between User domain models and JPA entities.
 * Handles the bidirectional conversion of user data structures.
 */
@Component
public class UserMapper {

    /**
     * Converts a User domain model to a UserEntity.
     * Maps all fields from the domain model to their corresponding entity representation.
     *
     * @param user The domain model to convert
     * @return The corresponding JPA entity
     */
    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    /**
     * Converts a UserEntity to a User domain model.
     * Maps all fields from the entity to their corresponding domain model representation.
     *
     * @param entity The JPA entity to convert
     * @return The corresponding domain model
     */
    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
} 