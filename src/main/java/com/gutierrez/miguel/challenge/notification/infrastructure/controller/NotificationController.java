package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.SendNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SendNotificationService sendNotificationService;

    @PostMapping
    public ResponseEntity<Notification> sendNotification(SendNotificationRequest request) {
        return ResponseEntity.ok(sendNotificationService
                .execute(request.recipientId(), request.type(), request.message()));
    }

}
