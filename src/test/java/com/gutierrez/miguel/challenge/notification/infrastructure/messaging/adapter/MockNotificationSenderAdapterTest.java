package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MockNotificationSenderAdapterTest {

    private final MockNotificationSenderAdapter adapter = new MockNotificationSenderAdapter();

    @Test
    void shouldLogNotificationWithoutThrowing() {
        Notification notification = new Notification(
                UUID.randomUUID(),
                new NotificationRecipient(UUID.randomUUID()),
                new NotificationContent("Test message"),
                NotificationType.FRIEND_ACCEPTED,
                new NotificationTimestamp(LocalDateTime.now()),
                NotificationCategory.SOCIAL_EVENT
        );

        assertDoesNotThrow(() -> adapter.send(notification));
    }
}