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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller for managing notification preferences.
 * Provides endpoints for updating user notification preferences for different notification categories.
 * This controller handles HTTP requests related to notification preferences and delegates the business logic
 * to the appropriate service.
 */
@RestController
@RequestMapping("/notification-preferences")
@RequiredArgsConstructor
@Tag(name = "Notification Preferences", description = "Notification preferences management APIs")
public class NotificationPreferenceController {

    private final UpdateNotificationPreferenceService updateService;

    /**
     * Updates the notification preferences for a specific user and category.
     * This endpoint allows users to enable or disable notifications for specific categories
     * such as game events or social events.
     * 
     * The request should include:
     * - The notification category to update (e.g., GAME_EVENT, SOCIAL_EVENT)
     * - Whether notifications should be enabled or disabled for that category
     *
     * @param userId The UUID of the user whose preferences are being updated
     * @param request The preference update request containing category and enabled status
     * @return ResponseEntity with no content (204) on successful update
     */
    @Operation(
        summary = "Update notification preferences",
        description = "Updates the notification preferences for a specific user and category. " +
                     "This endpoint allows users to enable or disable notifications for specific categories " +
                     "such as game events or social events."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Preferences updated successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters or malformed request body"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error occurred while updating preferences"
        )
    })
    @PutMapping(value = "/update/{userId}")
    public ResponseEntity<Void> updatePreference(
            @PathVariable("userId") UUID userId,
            @Parameter(
                description = "Notification preference update details",
                required = true
            )
            @RequestBody UpdateNotificationPreferenceRequest request) {
        updateService.updatePreference(userId, request.category(), request.enabled());
        return ResponseEntity.noContent().build();
    }
}
