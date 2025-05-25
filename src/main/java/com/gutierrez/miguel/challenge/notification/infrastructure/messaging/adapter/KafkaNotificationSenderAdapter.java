package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationSenderPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("docker")
public class KafkaNotificationSenderAdapter implements NotificationSenderPort {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    @Override
    public void send(Notification notification) {
        String message = String.format("Type: %s | Recipient: %s | Content: %s",
                notification.getType().name(),
                notification.getRecipient().value(),
                notification.getContent().value()
        );

        kafkaTemplate.send(topic, message);
        log.info("Notification sent to Kafka {} and topic {}", message, topic);
    }
}
