package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationPreferenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * JPA implementation of the NotificationPreferenceRepositoryPort interface.
 * This adapter handles the persistence of notification preferences using JPA repositories.
 * It converts between domain models and JPA entities using the NotificationPreferenceMapper.
 * 
 * The adapter follows the hexagonal architecture pattern, implementing the port interface
 * and translating between domain models and infrastructure-specific entities.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JpaNotificationPreferencesRepositoryAdapter implements NotificationPreferenceRepositoryPort {

    private final JpaNotificationPreferencesRepository repository;
    private final NotificationPreferenceMapper mapper;

    /**
     * Saves a notification preference to the database.
     * This method performs the following steps:
     * 1. Converts the domain model to a JPA entity
     * 2. Persists the entity to the database
     * 3. Logs the operation at appropriate levels
     *
     * @param preference The notification preference domain model to be saved
     * @throws RuntimeException if there is an error during the save operation
     */
    @Override
    public void save(NotificationPreferences preference) {
        log.debug("Converting notification preference to entity - ID: {}", preference.getId());
        NotificationPreferencesEntity entity = mapper.toEntity(preference);

        try {
            log.debug("Saving notification preference to database - ID: {}", entity.getId());
            repository.save(entity);
            log.debug("Successfully saved notification preference to database - ID: {}", entity.getId());
        } catch (Exception e) {
            log.error("Error saving notification preference to database - ID: {}, error: {}",
                    preference.getId(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves all notification preferences for a specific user.
     * This method performs the following steps:
     * 1. Queries the database for preferences matching the user ID
     * 2. Converts the JPA entities to domain models
     * 3. Logs the operation at appropriate levels
     *
     * @param userId The UUID of the user
     * @return List of notification preferences for the user
     * @throws RuntimeException if there is an error during the retrieval operation
     */
    @Override
    public List<NotificationPreferences> findByUserId(UUID userId) {
        log.debug("Finding notification preferences for user: {}", userId);

        try {
            List<NotificationPreferencesEntity> entities = repository.findByUserId(userId);
            log.debug("Found {} notification preferences for user: {}",
                    entities.size(), userId);

            List<NotificationPreferences> preferences = entities.stream()
                    .map(mapper::toDomain)
                    .toList();

            log.debug("Converted {} entities to domain models for user: {}",
                    preferences.size(), userId);

            return preferences;
        } catch (Exception e) {
            log.error("Error finding notification preferences for user: {}, error: {}",
                    userId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Checks if notifications are enabled for a specific user and category.
     * This method performs the following steps:
     * 1. Queries the database for a preference matching the user ID and category
     * 2. Returns the enabled status if found, or true (default) if not found
     * 3. Logs the operation at appropriate levels
     *
     * @param userId The UUID of the user
     * @param category The notification category to check
     * @return true if notifications are enabled, false otherwise
     * @throws RuntimeException if there is an error during the check operation
     */
    @Override
    public boolean isEnabled(UUID userId, NotificationCategory category) {
        log.debug("Checking if notifications are enabled for user: {}, category: {}",
                userId, category);

        try {
            boolean enabled = repository.findByUserIdAndCategory(userId, category)
                    .map(NotificationPreferencesEntity::isEnabled)
                    .orElse(true);

            log.debug("Notification preference check result - user: {}, category: {}, enabled: {}",
                    userId, category, enabled);

            return enabled;
        } catch (Exception e) {
            log.error("Error checking notification preference - user: {}, category: {}, error: {}",
                    userId, category, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public NotificationPreferences findByUserIdAndCategory(UUID userId, NotificationCategory category) {
        log.debug("Finding notification preferences for user: {} and category {}", userId, category.name());

        try {
            NotificationPreferencesEntity entities = repository.findByUserIdAndCategory(userId, category)
                    .orElse(null);
            log.debug("Found notification preferences for user {} and category {}",
                    userId, category.name());

            if (entities == null) {
                return null;
            }

            NotificationPreferences preferences = mapper.toDomain(entities);

            log.debug("Converted entities to domain models for user {}", userId);

            return preferences;
        } catch (Exception e) {
            log.error("Error finding notification preferences for user: {} and category {}, error: {}",
                    userId, category.name(), e.getMessage(), e);
            throw e;
        }
    }
}