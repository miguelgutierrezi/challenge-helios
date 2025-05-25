package com.gutierrez.miguel.challenge.user.application.usecases;

import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.domain.ports.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUsersServiceTest {

    @Mock
    private UserRepositoryPort repository;

    @InjectMocks
    private GetAllUsersService service;

    private List<User> mockUsers;

    @BeforeEach
    void setUp() {
        mockUsers = List.of(
                User.builder().id(UUID.randomUUID()).name("Alice").email("alice@example.com").build(),
                User.builder().id(UUID.randomUUID()).name("Bob").email("bob@example.com").build()
        );
    }

    @Test
    void shouldReturnAllUsers() {
        when(repository.findAll()).thenReturn(mockUsers);

        List<User> result = service.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionIfRepositoryFails() {
        when(repository.findAll()).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, service::getAllUsers);

        assertEquals("DB error", exception.getMessage());
        verify(repository, times(1)).findAll();
    }
}