package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationRepositoryPort;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Adapter implementation for the NotificationRepositoryPort interface.
 * This class handles the persistence of notifications using JPA.
 * It converts between domain models and JPA entities using the NotificationMapper.
 */
@Component
@RequiredArgsConstructor
@Slf4j
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
        log.debug("Converting notification to entity - ID: {}", notification.getId());
        NotificationEntity entity = mapper.toEntity(notification);
        
        try {
            log.debug("Saving notification entity to database - ID: {}", entity.getId());
            NotificationEntity saved = jpaRepository.save(entity);
            log.debug("Successfully saved notification to database - ID: {}", saved.getId());
            
            Notification domainNotification = mapper.toDomain(saved);
            log.debug("Converted saved entity back to domain model - ID: {}", domainNotification.getId());
            
            return domainNotification;
        } catch (Exception e) {
            log.error("Error saving notification to database - ID: {}, error: {}", 
                    notification.getId(), e.getMessage(), e);
            throw e;
        }
    }
}
