package com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;

import java.util.UUID;

public record SendNotificationRequest(UUID recipientId, NotificationType type, String message) {
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
