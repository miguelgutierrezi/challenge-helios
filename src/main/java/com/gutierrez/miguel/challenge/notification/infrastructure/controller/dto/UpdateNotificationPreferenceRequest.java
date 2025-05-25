package com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;

import java.util.UUID;

public record UpdateNotificationPreferenceRequest(
        UUID cardholderId,
        NotificationCategory category,
        boolean enabled
) {
}