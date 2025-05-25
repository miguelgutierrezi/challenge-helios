package com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between Notification domain models and JPA entities.
 * Handles the bidirectional conversion of all notification-related data structures.
 */
@Component
public class NotificationMapper {

    /**
     * Converts a Notification domain model to a NotificationEntity.
     * Maps all fields from the domain model to their corresponding entity representation.
     *
     * @param notification The domain model to convert
     * @return The corresponding JPA entity
     */
    public NotificationEntity toEntity(Notification notification) {
        return NotificationEntity.builder()
                .id(notification.getId())
                .type(notification.getType().name())
                .content(notification.getContent().value())
                .recipient(notification.getRecipient().value())
                .timestamp(notification.getTimestamp().value())
                .category(notification.getCategory().name())
                .build();
    }

    /**
     * Converts a NotificationEntity to a Notification domain model.
     * Maps all fields from the entity to their corresponding domain model representation.
     *
     * @param entity The JPA entity to convert
     * @return The corresponding domain model
     */
    public Notification toDomain(NotificationEntity entity) {
        return Notification.builder()
                .id(entity.getId())
                .type(NotificationType.valueOf(entity.getType()))
                .content(new NotificationContent(entity.getContent()))
                .recipient(new NotificationRecipient(entity.getRecipient()))
                .timestamp(new NotificationTimestamp(entity.getTimestamp()))
                .category(NotificationCategory.valueOf(entity.getCategory()))
                .build();
    }
}
