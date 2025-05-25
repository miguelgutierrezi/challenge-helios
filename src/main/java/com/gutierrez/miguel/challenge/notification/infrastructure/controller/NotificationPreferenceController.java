package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.gutierrez.miguel.challenge.notification.application.usecases.UpdateNotificationPreferenceService;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.UpdateNotificationPreferenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification-preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController {

    private final UpdateNotificationPreferenceService updateService;

    @PostMapping
    public ResponseEntity<Void> updatePreference(@RequestBody UpdateNotificationPreferenceRequest request) {
        updateService.updatePreference(request.cardholderId(), request.category(), request.enabled());
        return ResponseEntity.ok().build();
    }
}
