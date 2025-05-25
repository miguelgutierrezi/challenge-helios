package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.listener;

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

    @KafkaListener(
            topics = "${app.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleMessage(String message) {
        log.info("Received Kafka message: {}", message);

        try {
            NotificationPayload payload = parseMessage(message);
            log.info("Notification processed and stored: {}", payload);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid Kafka message format or data: {}", message);
        } catch (Exception ex) {
            log.error("Unexpected error while processing Kafka message: {}", message, ex);
        }
    }

    private NotificationPayload parseMessage(String message) {
        try {
            String[] parts = message.split("\\|");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Message must have 3 parts: Type, Recipient, Content");
            }

            String typePart = parts[0].trim();
            String recipientPart = parts[1].trim();
            String contentPart = parts[2].trim();

            NotificationType type = NotificationType.valueOf(typePart.split(":")[1].trim());
            UUID recipientId = UUID.fromString(recipientPart.split(":")[1].trim());
            String content = contentPart.split(":", 2)[1].trim();

            return new NotificationPayload(recipientId, type, content);
        } catch (Exception e) {
            throw new IllegalArgumentException("Malformed Kafka message: " + message);
        }
    }

    private record NotificationPayload(UUID recipientId, NotificationType type, String content) {
    }
}