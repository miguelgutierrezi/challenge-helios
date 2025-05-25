package com.gutierrez.miguel.challenge.notification.application.usecases;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for updating notification preferences.
 * This service handles the business logic for updating user notification preferences,
 * including validation and persistence of preference changes.
 *
 * The service follows the hexagonal architecture pattern, using ports for external dependencies
 * and focusing on the core business logic of preference management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateNotificationPreferenceService {

    private final NotificationPreferenceRepositoryPort repository;

    /**
     * Updates a user's notification preference for a specific category.
     * This method handles the creation or update of notification preferences,
     * ensuring that user preferences are properly persisted.
     *
     * The method performs the following steps:
     * 1. Creates a new NotificationPreferences object with the provided parameters
     * 2. Sets the user ID, category, and enabled status
     * 3. Saves the preference to the repository
     * 4. Logs the operation at appropriate levels
     *
     * @param userId The UUID of the user whose preference is being updated
     * @param category The notification category to update
     * @param enabled Whether notifications should be enabled for this category
     * @throws RuntimeException if there is an error updating the preference
     */
    public void updatePreference(UUID userId, NotificationCategory category, boolean enabled) {
        log.info("Updating notification preferences - user: {}, category: {}, enabled: {}",
                userId, category, enabled);

        try {
            NotificationPreferences existing = repository.findByUserIdAndCategory(userId, category);

            if (existing != null) {
                existing.setEnabled(enabled);
                repository.save(existing);
                log.info("Updated existing preference for user: {}, category: {}", userId, category);
            } else {
                NotificationPreferences preference = new NotificationPreferences(
                        UUID.randomUUID(),
                        userId,
                        category,
                        enabled
                );
                log.debug("Created new preference object with ID: {}", preference.getId());
                repository.save(preference);
                log.info("Saved new preference for user: {}, category: {}", userId, category);
            }
        } catch (Exception e) {
            log.error("Error updating notification preference for user: {}, category: {}, error: {}",
                    userId, category, e.getMessage(), e);
            throw e;
        }
    }
}