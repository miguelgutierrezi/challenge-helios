package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationPreferenceMapper;
import lombok.RequiredArgsConstructor;
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
        repository.save(mapper.toEntity(preference));
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
        return repository.findByCardholderId(cardholderId).stream()
                .map(mapper::toDomain)
                .toList();
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
        return repository.findByCardholderIdAndCategory(cardholderId, category)
                .map(NotificationPreferencesEntity::isEnabled)
                .orElse(true);
    }
}