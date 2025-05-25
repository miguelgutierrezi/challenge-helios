package com.gutierrez.miguel.challenge.notification.application.usecases;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationRepositoryPort;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationSenderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendNotificationServiceTest {

    @Mock
    private NotificationRepositoryPort repository;

    @Mock
    private NotificationSenderPort sender;

    @Mock
    private NotificationPreferenceRepositoryPort preferenceRepository;

    @InjectMocks
    private SendNotificationService service;

    private UUID userId;
    private NotificationType type;
    private String content;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        type = NotificationType.LEVEL_UP;
        content = "You have a new level!";
    }

    @Test
    void shouldSendNotificationWhenPreferenceIsEnabled() {
        when(preferenceRepository.isEnabled(userId, type.getCategory())).thenReturn(true);

        Notification result = service.execute(userId, type, content);

        assertNotNull(result);
        assertEquals(userId, result.getRecipient().value());
        assertEquals(content, result.getContent().value());
        assertEquals(type, result.getType());
        assertEquals(type.getCategory(), result.getCategory());

        verify(repository).save(any(Notification.class));
        verify(sender).send(any(Notification.class));
    }

    @Test
    void shouldNotSendNotificationWhenPreferenceIsDisabled() {
        when(preferenceRepository.isEnabled(userId, type.getCategory())).thenReturn(false);

        Notification result = service.execute(userId, type, content);

        assertNull(result);
        verify(repository, never()).save(any());
        verify(sender, never()).send(any());
    }

    @Test
    void shouldThrowExceptionWhenRepositoryFails() {
        when(preferenceRepository.isEnabled(userId, type.getCategory())).thenReturn(true);
        doThrow(new RuntimeException("DB error")).when(repository).save(any(Notification.class));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.execute(userId, type, content));
        assertEquals("DB error", exception.getMessage());

        verify(repository).save(any());
        verify(sender, never()).send(any());
    }
}