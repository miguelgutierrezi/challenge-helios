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
     * Retrieves all notification preferences for a specific cardholder.
     * Converts the JPA entities back to domain models.
     *
     * @param cardholderId The UUID of the cardholder
     * @return List of notification preferences for the cardholder
     */
    @Override
    public List<NotificationPreferences> findByCardholderId(UUID cardholderId) {
        log.debug("Finding notification preferences for cardholder: {}", cardholderId);
        
        try {
            List<NotificationPreferencesEntity> entities = repository.findByCardholderId(cardholderId);
            log.debug("Found {} notification preferences for cardholder: {}", 
                    entities.size(), cardholderId);
            
            List<NotificationPreferences> preferences = entities.stream()
                    .map(mapper::toDomain)
                    .toList();
            
            log.debug("Converted {} entities to domain models for cardholder: {}", 
                    preferences.size(), cardholderId);
            
            return preferences;
        } catch (Exception e) {
            log.error("Error finding notification preferences for cardholder: {}, error: {}", 
                    cardholderId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Checks if notifications are enabled for a specific cardholder and category.
     * Returns true if no preference is found (default behavior).
     *
     * @param cardholderId The UUID of the cardholder
     * @param category The notification category to check
     * @return true if notifications are enabled, false otherwise
     */
    @Override
    public boolean isEnabled(UUID cardholderId, NotificationCategory category) {
        log.debug("Checking if notifications are enabled for cardholder: {}, category: {}", 
                cardholderId, category);
        
        try {
            boolean enabled = repository.findByCardholderIdAndCategory(cardholderId, category)
                    .map(NotificationPreferencesEntity::isEnabled)
                    .orElse(true);
            
            log.debug("Notification preference check result - cardholder: {}, category: {}, enabled: {}", 
                    cardholderId, category, enabled);
            
            return enabled;
        } catch (Exception e) {
            log.error("Error checking notification preference - cardholder: {}, category: {}, error: {}", 
                    cardholderId, category, e.getMessage(), e);
            throw e;
        }
    }
}