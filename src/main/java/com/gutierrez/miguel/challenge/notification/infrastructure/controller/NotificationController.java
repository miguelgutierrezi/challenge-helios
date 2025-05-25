package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.SendNotificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing notifications in the system.
 * Provides endpoints for sending notifications to users.
 */
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "Notification management APIs")
public class NotificationController {

    private final SendNotificationService sendNotificationService;

    /**
     * Sends a notification to a specific recipient.
     *
     * @param request The notification request containing recipient ID, type, and message
     * @return ResponseEntity containing the created notification
     */
    @Operation(
        summary = "Send a notification",
        description = "Sends a notification to a specific recipient with the given type and message"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Notification sent successfully",
            content = @Content(schema = @Schema(implementation = Notification.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters"
        )
    })
    @PostMapping
    public ResponseEntity<Notification> sendNotification(
            @Parameter(description = "Notification request details", required = true)
            SendNotificationRequest request) {
        return ResponseEntity.ok(sendNotificationService
                .execute(request.recipientId(), request.type(), request.message()));
    }
}
