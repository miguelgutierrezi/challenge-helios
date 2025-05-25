package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.listener;

import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@Profile("docker")
@RequiredArgsConstructor
public class KafkaNotificationListener {

    private final SendNotificationService sendNotificationService;

    @KafkaListener(
            topics = "${app.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleMessage(String message) {
        log.info("Received Kafka message: {}", message);

        try {
            NotificationPayload payload = parseMessage(message);
            Notification notification = sendNotificationService.execute(
                    payload.recipientId(),
                    payload.type(),
                    payload.content()
            );
            log.info("Notification processed and stored: {}", notification);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid Kafka message format or data: {}", message);
        } catch (Exception ex) {
            log.error("Unexpected error while processing Kafka message: {}", message, ex);
        }
    }

    private NotificationPayload parseMessage(String message) {
        String[] parts = message.split("\\|");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Message must have 3 parts: UUID|TYPE|CONTENT");
        }

        UUID recipientId = UUID.fromString(parts[0].trim());
        NotificationType type = NotificationType.valueOf(parts[1].trim());
        String content = parts[2].trim();

        return new NotificationPayload(recipientId, type, content);
    }

    private record NotificationPayload(UUID recipientId, NotificationType type, String content) {
    }
}