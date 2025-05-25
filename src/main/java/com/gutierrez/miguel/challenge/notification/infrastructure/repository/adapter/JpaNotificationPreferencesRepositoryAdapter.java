package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationPreferenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Adapter implementation for the NotificationPreferenceRepositoryPort interface.
 * This class handles the persistence of notification preferences using JPA.
 * It converts between domain models and JPA entities using the NotificationPreferenceMapper.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JpaNotificationPreferencesRepositoryAdapter implements NotificationPreferenceRepositoryPort {

    private final JpaNotificationPreferencesRepository repository;
    private final NotificationPreferenceMapper mapper;

    /**
     * Saves a notification preference to the database.
     * Converts the domain model to a JPA entity and persists it.
     *
     * @param preference The notification preference domain model to be saved
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
     * Converts the JPA entities back to domain models.
     *
     * @param userId The UUID of the user
     * @return List of notification preferences for the user
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
     * Returns true if no preference is found (default behavior).
     *
     * @param userId   The UUID of the user
     * @param category The notification category to check
     * @return true if notifications are enabled, false otherwise
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
}