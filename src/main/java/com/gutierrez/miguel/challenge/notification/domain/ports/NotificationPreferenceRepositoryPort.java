package com.gutierrez.miguel.challenge.notification.domain.ports;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;

import java.util.List;
import java.util.UUID;

/**
 * Port interface for notification preference repository operations.
 * Defines the contract for managing notification preferences in the system.
 * This interface is part of the hexagonal architecture, providing a boundary
 * between the domain and infrastructure layers.
 */
public interface NotificationPreferenceRepositoryPort {
    /**
     * Saves a notification preference to the repository.
     * If a preference for the same user and category already exists,
     * it will be updated with the new enabled status.
     *
     * @param preference The notification preference to save
     * @throws RuntimeException if there is an error saving the preference
     */
    void save(NotificationPreferences preference);

    /**
     * Retrieves all notification preferences for a specific user.
     *
     * @param userId The UUID of the user
     * @return List of notification preferences for the user
     * @throws RuntimeException if there is an error retrieving the preferences
     */
    List<NotificationPreferences> findByUserId(UUID userId);

    /**
     * Checks if notifications are enabled for a specific user and category.
     * If no preference is found for the given user and category,
     * returns true (default behavior).
     *
     * @param userId The UUID of the user
     * @param category The notification category to check
     * @return true if notifications are enabled, false otherwise
     * @throws RuntimeException if there is an error checking the preference
     */
    boolean isEnabled(UUID userId, NotificationCategory category);

    NotificationPreferences findByUserIdAndCategory(UUID userId, NotificationCategory category);
}
