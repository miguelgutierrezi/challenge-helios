package com.gutierrez.miguel.challenge.notification.application.usecases;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
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
 * Service for sending notifications.
 * This service handles the business logic for sending notifications to users,
 * including preference checking, notification creation, and persistence.
 *
 * The service follows the hexagonal architecture pattern, using ports for external dependencies
 * and focusing on the core business logic of notification management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotificationService {

    private final NotificationRepositoryPort notificationRepository;
    private final NotificationSenderPort sender;
    private final NotificationPreferenceRepositoryPort preferenceRepository;

    /**
     * Sends a notification to a specific user.
     * This method handles the complete notification sending process, including:
     * 1. Checking if the user has enabled notifications for the given category
     * 2. Creating a new notification with the provided details
     * 3. Persisting the notification
     * 4. Logging the operation at appropriate levels
     *
     * @param recipientId The UUID of the user who will receive the notification
     * @param type The type of notification to send
     * @param message The content of the notification
     * @return The created notification
     * @throws RuntimeException if there is an error sending the notification
     */
    public Notification execute(UUID recipientId, NotificationType type, String message) {
        log.info("Sending notification to user: {}, type: {}", recipientId, type);

        try {
            // Check if notifications are enabled for this category
            boolean enabled = preferenceRepository.isEnabled(recipientId, type.getCategory());
            if (!enabled) {
                log.info("Notifications are disabled for user: {}, category: {}", 
                        recipientId, type.getCategory());
                return null;
            }

            // Create and save the notification
            Notification notification = Notification.builder()
                    .id(UUID.randomUUID())
                    .recipient(new NotificationRecipient(recipientId))
                    .content(new NotificationContent(message))
                    .type(type)
                    .timestamp(new NotificationTimestamp(LocalDateTime.now()))
                    .category(type.getCategory())
                    .build();

            notificationRepository.save(notification);
            log.info("Successfully sent notification to user: {}, type: {}", recipientId, type);

            sender.send(notification);
            log.info("Notification successfully sent to recipient: {}, ID: {}", recipientId, notification.getId());

            return notification;
        } catch (Exception e) {
            log.error("Error sending notification to user: {}, type: {}, error: {}",
                    recipientId, type, e.getMessage(), e);
            throw e;
        }
    }
}
