package com.gutierrez.miguel.challenge.notification.domain.model.vo;

import java.time.LocalDateTime;

public record NotificationTimestamp(LocalDateTime value) {
    public NotificationTimestamp {
        if (value == null) {
            throw new IllegalArgumentException("Notification date must not be null");
        }
    }
}
