package com.gutierrez.miguel.challenge.user.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.user.domain.exception.UserNotFoundException;
import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.infrastructure.repository.entity.UserEntity;
import com.gutierrez.miguel.challenge.user.infrastructure.repository.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaUserRepositoryAdapterTest {

    @Mock
    private JpaUserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private JpaUserRepositoryAdapter adapter;

    private UUID userId;
    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        user = User.builder().id(userId).name("Alice").email("alice@example.com").build();
        userEntity = UserEntity.builder().id(userId).name("Alice").email("alice@example.com").build();
    }

    @Test
    void shouldSaveUserSuccessfully() {
        when(mapper.toEntity(user)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toDomain(userEntity)).thenReturn(user);

        User result = adapter.save(user);

        assertEquals(user, result);
        verify(repository).save(userEntity);
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        when(repository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mapper.toDomain(userEntity)).thenReturn(user);

        User result = adapter.findById(userId);

        assertEquals(user, result);
        verify(repository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(repository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            adapter.findById(userId);
        });

        assertEquals("User with id %s not found".formatted(userId), exception.getMessage());
    }

    @Test
    void shouldFindAllUsers() {
        List<UserEntity> entities = List.of(userEntity);
        List<User> users = List.of(user);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDomain(userEntity)).thenReturn(user);

        List<User> result = adapter.findAll();

        assertEquals(users, result);
        verify(repository).findAll();
    }
}