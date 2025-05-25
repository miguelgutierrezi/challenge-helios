package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationRepositoryPort;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adapter implementation for the NotificationRepositoryPort interface.
 * This class handles the persistence of notifications using JPA.
 * It converts between domain models and JPA entities using the NotificationMapper.
 */
@Component
@RequiredArgsConstructor
public class JpaNotificationRepositoryAdapter implements NotificationRepositoryPort {

    private final JpaNotificationRepository jpaRepository;
    private final NotificationMapper mapper;

    /**
     * Saves a notification to the database.
     * Converts the domain model to a JPA entity, persists it, and converts it back.
     *
     * @param notification The notification domain model to be saved
     * @return The saved notification with any database-generated fields
     */
    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = mapper.toEntity(notification);
        NotificationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
