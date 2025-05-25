package com.gutierrez.miguel.challenge.notification.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreferences {
    private UUID id;
    private UUID userId;
    private NotificationCategory category;
    private boolean enabled;
}
