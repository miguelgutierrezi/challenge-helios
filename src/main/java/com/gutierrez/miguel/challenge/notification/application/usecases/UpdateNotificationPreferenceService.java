package com.gutierrez.miguel.challenge.notification.application.usecases;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for managing notification preferences.
 * This service handles the business logic for updating user notification preferences
 * for different notification categories.
 */
@Service
@RequiredArgsConstructor
public class UpdateNotificationPreferenceService {

    private final NotificationPreferenceRepositoryPort repository;

    /**
     * Updates the notification preferences for a specific user and category.
     * Creates a new preference entry if it doesn't exist, or updates the existing one.
     *
     * @param cardholderId The UUID of the cardholder whose preferences are being updated
     * @param category The notification category to update preferences for
     * @param enabled Whether notifications for this category should be enabled
     */
    public void updatePreference(UUID cardholderId, NotificationCategory category, boolean enabled) {
        NotificationPreferences preference = new NotificationPreferences(UUID.randomUUID(), cardholderId, category, enabled);
        repository.save(preference);
    }
}