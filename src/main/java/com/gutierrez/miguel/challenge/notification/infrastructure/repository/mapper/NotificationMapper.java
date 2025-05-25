package com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationEntity toEntity(Notification notification) {
        return NotificationEntity.builder()
                .id(notification.getId())
                .type(notification.getType().name())
                .content(notification.getContent().value())
                .recipient(notification.getRecipient().value())
                .timestamp(notification.getTimestamp().value())
                .build();
    }

    public Notification toDomain(NotificationEntity entity) {
        return Notification.builder()
                .id(entity.getId())
                .type(NotificationType.valueOf(entity.getType()))
                .content(new NotificationContent(entity.getContent()))
                .recipient(new NotificationRecipient(entity.getRecipient()))
                .timestamp(new NotificationTimestamp(entity.getTimestamp()))
                .build();
    }
}
