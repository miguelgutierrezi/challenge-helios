package com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SendNotificationResponse(UUID id,
                                       UUID recipient,
                                       String content,
                                       String type,
                                       LocalDateTime timestamp,
                                       String category) {
}
