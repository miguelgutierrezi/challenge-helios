package com.gutierrez.miguel.challenge.notification.domain.model.vo;

public record NotificationContent(String value) {
    public NotificationContent {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Notification content must not be blank");
        }
    }
}
