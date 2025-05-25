package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.gutierrez.miguel.challenge.notification.application.usecases.UpdateNotificationPreferenceService;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.UpdateNotificationPreferenceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing notification preferences.
 * Provides endpoints for updating user notification preferences.
 */
@RestController
@RequestMapping("/notification-preferences")
@RequiredArgsConstructor
@Tag(name = "Notification Preferences", description = "Notification preferences management APIs")
public class NotificationPreferenceController {

    private final UpdateNotificationPreferenceService updateService;

    /**
     * Updates the notification preferences for a specific user and category.
     *
     * @param request The preference update request containing user ID, category, and enabled status
     * @return ResponseEntity with no content on success
     */
    @Operation(
        summary = "Update notification preferences",
        description = "Updates the notification preferences for a specific user and category"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Preferences updated successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters"
        )
    })
    @PostMapping
    public ResponseEntity<Void> updatePreference(
            @Parameter(description = "Notification preference update details", required = true)
            @RequestBody UpdateNotificationPreferenceRequest request) {
        updateService.updatePreference(request.userId(), request.category(), request.enabled());
        return ResponseEntity.ok().build();
    }
}
