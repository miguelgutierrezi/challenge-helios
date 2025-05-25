package com.gutierrez.miguel.challenge.notification.application.usecases;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for managing notification preferences.
 * This service handles the business logic for updating user notification preferences
 * for different notification categories.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateNotificationPreferenceService {

    private final NotificationPreferenceRepositoryPort repository;

    /**
     * Updates the notification preferences for a specific user and category.
     * This method performs the following steps:
     * 1. Creates a new NotificationPreferences object with a generated UUID
     * 2. Sets the user ID, category, and enabled status
     * 3. Saves the preference to the repository
     * 
     * The preference will be created if it doesn't exist, or updated if it already exists.
     * This behavior is handled by the repository implementation.
     *
     * @param userId   The UUID of the user whose preferences are being updated
     * @param category The notification category to update preferences for (e.g., GAME_EVENT, SOCIAL_EVENT)
     * @param enabled  Whether notifications for this category should be enabled (true) or disabled (false)
     * @throws RuntimeException if there is an error saving the preference
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
            log.error("Error updating notification preferences - user: {}, category: {}, error: {}",
                    userId, category, e.getMessage(), e);
            throw e;
        }
    }
}