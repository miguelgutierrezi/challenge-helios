package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.gutierrez.miguel.challenge.notification.application.usecases.UpdateNotificationPreferenceService;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.UpdateNotificationPreferenceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST controller for managing notification preferences.
 * This controller provides endpoints for updating user notification preferences,
 * allowing users to enable or disable notifications for specific categories.
 *
 * The controller follows REST principles and uses proper HTTP methods and status codes.
 * All endpoints are documented using OpenAPI/Swagger annotations for API documentation.
 */
@RestController
@RequestMapping("/notification-preferences")
@RequiredArgsConstructor
@Tag(name = "Notification Preferences", description = "APIs for managing notification preferences")
public class NotificationPreferenceController {

    private final UpdateNotificationPreferenceService updateNotificationPreferenceService;

    /**
     * Updates a user's notification preference for a specific category.
     * This endpoint allows enabling or disabling notifications for a given category.
     *
     * @param userId The UUID of the user whose preference is being updated
     * @param request The request containing the category and enabled status
     * @return ResponseEntity with no content (204) on success
     */
    @PutMapping("/update/{userId}")
    @Operation(
            summary = "Update notification preference",
            description = "Updates a user's notification preference for a specific category"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Preference updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> updatePreference(
            @Parameter(description = "User ID", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Notification preference details", required = true)
            @Valid @RequestBody UpdateNotificationPreferenceRequest request
    ) {
        updateNotificationPreferenceService.updatePreference(
                userId,
                request.category(),
                request.enabled()
        );
        return ResponseEntity.noContent().build();
    }
}
