package com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationMapperTest {

    private NotificationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NotificationMapper();
    }

    @Test
    void shouldMapDomainToEntity() {
        Notification notification = new Notification(
                UUID.randomUUID(),
                new NotificationRecipient(UUID.randomUUID()),
                new NotificationContent("Test content"),
                NotificationType.LEVEL_UP,
                new NotificationTimestamp(LocalDateTime.now()),
                NotificationCategory.GAME_EVENT
        );

        NotificationEntity entity = mapper.toEntity(notification);

        assertEquals(notification.getId(), entity.getId());
        assertEquals(notification.getType().name(), entity.getType());
        assertEquals(notification.getContent().value(), entity.getContent());
        assertEquals(notification.getRecipient().value(), entity.getRecipient());
        assertEquals(notification.getTimestamp().value(), entity.getTimestamp());
        assertEquals(notification.getCategory().name(), entity.getCategory());
    }

    @Test
    void shouldMapEntityToDomain() {
        NotificationEntity entity = NotificationEntity.builder()
                .id(UUID.randomUUID())
                .type("FRIEND_REQUEST")
                .content("Welcome to the platform")
                .recipient(UUID.randomUUID())
                .timestamp(LocalDateTime.now())
                .category("SOCIAL_EVENT")
                .build();

        Notification domain = mapper.toDomain(entity);

        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getType(), domain.getType().name());
        assertEquals(entity.getContent(), domain.getContent().value());
        assertEquals(entity.getRecipient(), domain.getRecipient().value());
        assertEquals(entity.getTimestamp(), domain.getTimestamp().value());
        assertEquals(entity.getCategory(), domain.getCategory().name());
    }
}