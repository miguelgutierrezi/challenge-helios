package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaNotificationSenderAdapterTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaNotificationSenderAdapter senderAdapter;

    private final String topic = "game-notifications";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(senderAdapter, "topic", topic);
    }

    @Test
    void shouldSendNotificationToKafka() {
        UUID recipientId = UUID.randomUUID();
        Notification notification = new Notification(
                UUID.randomUUID(),
                new NotificationRecipient(recipientId),
                new NotificationContent("You've got mail!"),
                NotificationType.LEVEL_UP,
                new NotificationTimestamp(LocalDateTime.now()),
                NotificationCategory.GAME_EVENT
        );

        senderAdapter.send(notification);

        String expectedMessage = String.format("Type: %s | Recipient: %s | Content: %s",
                notification.getType().name(),
                notification.getRecipient().value(),
                notification.getContent().value()
        );

        verify(kafkaTemplate).send(topic, expectedMessage);
    }
}