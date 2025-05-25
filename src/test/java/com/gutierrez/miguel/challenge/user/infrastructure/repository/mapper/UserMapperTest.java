package com.gutierrez.miguel.challenge.user.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.infrastructure.repository.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapper();
    }

    @Test
    void shouldMapDomainToEntity() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john@example.com")
                .build();

        UserEntity entity = mapper.toEntity(user);

        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getName(), entity.getName());
        assertEquals(user.getEmail(), entity.getEmail());
    }

    @Test
    void shouldMapEntityToDomain() {
        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .name("Jane Smith")
                .email("jane@example.com")
                .build();

        User user = mapper.toDomain(entity);

        assertEquals(entity.getId(), user.getId());
        assertEquals(entity.getName(), user.getName());
        assertEquals(entity.getEmail(), user.getEmail());
    }
}