package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.simulation;

import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockNotificationEventSimulatorTest {

    @Mock
    private SendNotificationService sendNotificationService;

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private MockNotificationEventSimulator simulator;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        User user = User.builder().id(userId).name("Test User").email("test@example.com").build();
        when(userRepository.findAll()).thenReturn(List.of(user));
    }

    @Test
    void shouldSimulateNotificationEventWhenUsersExist() {
        when(sendNotificationService.execute(any(), any(), any())).thenReturn(mock(Notification.class));

        simulator.simulateNotificationEvent();

        verify(userRepository, times(1)).findAll();
        verify(sendNotificationService, times(1)).execute(any(), any(), any());
    }

    @Test
    void shouldNotSendNotificationWhenNoUsersFound() {
        when(userRepository.findAll()).thenReturn(List.of());

        simulator.simulateNotificationEvent();

        verify(sendNotificationService, never()).execute(any(), any(), any());
    }
}