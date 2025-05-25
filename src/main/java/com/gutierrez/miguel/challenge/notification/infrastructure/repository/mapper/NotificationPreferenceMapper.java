package com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between NotificationPreferences domain models and JPA entities.
 * This mapper handles the bidirectional conversion of notification preference data structures,
 * ensuring proper translation between the domain and infrastructure layers.
 * 
 * The mapper is a crucial component in maintaining the separation between domain models
 * and persistence-specific entities in the hexagonal architecture.
 */
@Component
public class NotificationPreferenceMapper {

    /**
     * Converts a NotificationPreferences domain model to a NotificationPreferencesEntity.
     * This method maps all fields from the domain model to their corresponding entity representation,
     * ensuring that all necessary data is preserved during the conversion.
     *
     * @param preference The domain model to convert
     * @return The corresponding JPA entity with all fields mapped
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
     * This method maps all fields from the entity to their corresponding domain model representation,
     * ensuring that all necessary data is preserved during the conversion.
     *
     * @param entity The JPA entity to convert
     * @return The corresponding domain model with all fields mapped
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