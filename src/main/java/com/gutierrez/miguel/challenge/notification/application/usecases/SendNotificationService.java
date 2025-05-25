package com.gutierrez.miguel.challenge.notification.application.usecases;


import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
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
    private final NotificationPreferenceRepositoryPort preferenceRepository;

    public Notification execute(UUID recipientId, NotificationType type, String content) {
        NotificationCategory category = type.getCategory();

        boolean isEnabled = preferenceRepository.isEnabled(recipientId, category);

        if (!isEnabled) {
            log.info("Notification for {} skipped due to preference for category {}", recipientId, category);
            return null;
        }

        Notification notification = new Notification(
                UUID.randomUUID(),
                new NotificationRecipient(recipientId),
                new NotificationContent(content),
                type,
                new NotificationTimestamp(LocalDateTime.now()),
                category
        );

        repository.save(notification);
        sender.send(notification);

        log.info("Notification sent to {}", recipientId);
        return notification;
    }
}
