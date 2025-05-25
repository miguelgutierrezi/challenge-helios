package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.listener;

import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verifyNoInteractions;

class KafkaNotificationListenerTest {

    @Mock
    private SendNotificationService sendNotificationService;

    @InjectMocks
    private KafkaNotificationListener listener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessValidMessageWithoutError() {
        UUID recipientId = UUID.randomUUID();
        String message = String.format("Type: LEVEL_UP | Recipient: %s | Content: Welcome!", recipientId);

        assertDoesNotThrow(() -> listener.handleMessage(message));
    }

    @Test
    void shouldLogWarningForInvalidMessageFormat() {
        String malformedMessage = "Invalid message format";
        listener.handleMessage(malformedMessage);

        verifyNoInteractions(sendNotificationService);
    }

    @Test
    void shouldHandleExceptionGracefully() {
        String message = "Type: INVALID_TYPE | Recipient: not-a-uuid | Content: This will fail";
        listener.handleMessage(message);

        verifyNoInteractions(sendNotificationService);
    }
}