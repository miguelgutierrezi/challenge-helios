package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.SendNotificationRequest;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.SendNotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing notifications.
 * This controller provides endpoints for sending notifications to users,
 * supporting different types of notifications such as game events, social events, etc.
 *
 * The controller follows REST principles and uses proper HTTP methods and status codes.
 * All endpoints are documented using OpenAPI/Swagger annotations for API documentation.
 */
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "APIs for sending and managing notifications")
public class NotificationController {

    private final SendNotificationService sendNotificationService;

    /**
     * Sends a notification to a specific user.
     * This endpoint allows sending different types of notifications with custom messages.
     *
     * @param request The notification request containing recipient, type, and message
     * @return ResponseEntity containing the sent notification details
     */
    @PostMapping
    @Operation(
            summary = "Send notification",
            description = "Sends a notification to a specific user with the given type and message"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<SendNotificationResponse> sendNotification(
            @Parameter(description = "Notification details", required = true)
            @Valid @RequestBody SendNotificationRequest request
    ) {
        Notification notification = sendNotificationService.execute(
                request.recipientId(),
                request.type(),
                request.message()
        );

        SendNotificationResponse response = new SendNotificationResponse(
                notification.getId(),
                notification.getRecipient().value(),
                notification.getContent().value(),
                notification.getType().name(),
                notification.getTimestamp().value(),
                notification.getCategory().name()
        );

        return ResponseEntity.ok(response);
    }
}
