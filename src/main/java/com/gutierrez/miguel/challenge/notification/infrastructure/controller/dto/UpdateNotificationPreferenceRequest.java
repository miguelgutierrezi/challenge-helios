package com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * Data Transfer Object for updating notification preferences.
 * Contains the necessary information to update a user's notification preferences for a specific category.
 */
@Schema(description = "Request object for updating notification preferences")
public record UpdateNotificationPreferenceRequest(
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID userId,
    
    @Schema(description = "Category of notifications to update preferences for", example = "GAME_EVENT")
    NotificationCategory category,
    
    @Schema(description = "Whether notifications for this category should be enabled", example = "true")
    boolean enabled
) {
}