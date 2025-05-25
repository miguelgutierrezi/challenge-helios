package com.gutierrez.miguel.challenge.user.application.usecases;

import com.gutierrez.miguel.challenge.notification.application.usecases.UpdateNotificationPreferenceService;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.domain.ports.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepositoryPort repository;

    @Mock
    private UpdateNotificationPreferenceService notificationPreferenceService;

    @InjectMocks
    private CreateUserService service;

    private String name;
    private String email;

    @BeforeEach
    void setUp() {
        name = "John Doe";
        email = "john@example.com";
    }

    @Test
    void shouldCreateUserAndUpdateAllNotificationPreferences() {
        UUID generatedId = UUID.randomUUID();
        User savedUser = User.builder()
                .id(generatedId)
                .name(name)
                .email(email)
                .build();

        when(repository.save(any(User.class))).thenReturn(savedUser);

        User result = service.create(name, email);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());

        verify(repository).save(any(User.class));
        for (NotificationCategory category : NotificationCategory.values()) {
            verify(notificationPreferenceService).updatePreference(savedUser.getId(), category, true);
        }
    }

    @Test
    void shouldThrowExceptionIfRepositoryFails() {
        when(repository.save(any(User.class))).thenThrow(new RuntimeException("DB failure"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.create(name, email));

        assertEquals("DB failure", exception.getMessage());
        verify(notificationPreferenceService, never()).updatePreference(any(), any(), anyBoolean());
    }
}