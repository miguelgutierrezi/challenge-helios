package com.gutierrez.miguel.challenge.notification.domain.model.vo;

import java.util.UUID;

public record NotificationRecipient(UUID value) {
    public NotificationRecipient {
        if (value == null) {
            throw new IllegalArgumentException("Notification recipient ID must not be null");
        }
    }
}