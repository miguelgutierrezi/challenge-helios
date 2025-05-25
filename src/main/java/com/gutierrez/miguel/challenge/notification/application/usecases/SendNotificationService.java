package com.gutierrez.miguel.challenge.notification.application.usecases;


import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationRepositoryPort;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationSenderPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendNotificationService {

    private final NotificationRepositoryPort repository;
    private final NotificationSenderPort sender;

    public Notification execute(UUID recipientEmail, NotificationType type, String content) {
        Notification notification = new Notification(
                UUID.randomUUID(),
                new NotificationRecipient(recipientEmail),
                new NotificationContent(content),
                type,
                new NotificationTimestamp(LocalDateTime.now())
        );

        repository.save(notification);
        sender.send(notification);

        log.info("Notification sent to {}", recipientEmail);
        return notification;
    }
}
