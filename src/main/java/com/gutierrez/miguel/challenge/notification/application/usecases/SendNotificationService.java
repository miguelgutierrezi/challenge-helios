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

/**
 * Service responsible for sending notifications to users.
 * This service handles the business logic for creating and sending notifications,
 * including checking user preferences before sending.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SendNotificationService {

    private final NotificationRepositoryPort repository;
    private final NotificationSenderPort sender;
    private final NotificationPreferenceRepositoryPort preferenceRepository;

    /**
     * Executes the notification sending process.
     * This method performs the following steps:
     * 1. Checks if the user has enabled notifications for the given category
     * 2. If enabled, creates a new notification
     * 3. Saves the notification to the repository
     * 4. Sends the notification through the configured sender
     *
     * @param recipientId The UUID of the notification recipient
     * @param type The type of notification to be sent
     * @param content The content/message of the notification
     * @return The created and sent notification, or null if notifications are disabled for the category
     */
    public Notification execute(UUID recipientId, NotificationType type, String content) {
        log.info("Starting notification process for recipient: {}, type: {}", recipientId, type);
        
        NotificationCategory category = type.getCategory();
        log.debug("Notification category determined: {}", category);

        boolean isEnabled = preferenceRepository.isEnabled(recipientId, category);
        log.debug("Notification preference check - recipient: {}, category: {}, enabled: {}", 
                 recipientId, category, isEnabled);

        if (!isEnabled) {
            log.info("Notification skipped - recipient: {} has disabled notifications for category: {}", 
                    recipientId, category);
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
        log.debug("Notification object created with ID: {}", notification.getId());

        try {
            repository.save(notification);
            log.debug("Notification saved to repository with ID: {}", notification.getId());
            
            sender.send(notification);
            log.info("Notification successfully sent to recipient: {}, ID: {}", recipientId, notification.getId());
            
            return notification;
        } catch (Exception e) {
            log.error("Error processing notification for recipient: {}, type: {}, error: {}", 
                    recipientId, type, e.getMessage(), e);
            throw e;
        }
    }
}
