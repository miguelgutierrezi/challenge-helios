package com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between NotificationPreferences domain models and JPA entities.
 * Handles the bidirectional conversion of notification preference data structures.
 */
@Component
public class NotificationPreferenceMapper {

    /**
     * Converts a NotificationPreferences domain model to a NotificationPreferencesEntity.
     * Maps all fields from the domain model to their corresponding entity representation.
     *
     * @param preference The domain model to convert
     * @return The corresponding JPA entity
     */
    public NotificationPreferencesEntity toEntity(NotificationPreferences preference) {
        return NotificationPreferencesEntity.builder()
                .id(preference.getId())
                .userId(preference.getUserId())
                .category(preference.getCategory())
                .enabled(preference.isEnabled())
                .build();
    }

    /**
     * Converts a NotificationPreferencesEntity to a NotificationPreferences domain model.
     * Maps all fields from the entity to their corresponding domain model representation.
     *
     * @param entity The JPA entity to convert
     * @return The corresponding domain model
     */
    public NotificationPreferences toDomain(NotificationPreferencesEntity entity) {
        return NotificationPreferences.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .category(entity.getCategory())
                .enabled(entity.isEnabled())
                .build();
    }
}