package com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * Data Transfer Object for sending notifications.
 * Contains all necessary information to create and send a notification.
 */
@Schema(description = "Request object for sending notifications")
public record SendNotificationRequest(
    @Schema(description = "Unique identifier of the notification recipient", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID recipientId,
    
    @Schema(description = "Type of notification to be sent", example = "LEVEL_UP")
    NotificationType type,
    
    @Schema(description = "Content of the notification message", example = "You have been invited to play a game!")
    String message
) {
    public SendNotificationRequest {
        if (recipientId == null) {
            throw new IllegalArgumentException("Recipient id cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
    }
}
