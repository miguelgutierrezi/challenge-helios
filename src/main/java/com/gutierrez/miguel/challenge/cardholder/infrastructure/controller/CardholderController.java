package com.gutierrez.miguel.challenge.cardholder.infrastructure.controller;

import com.gutierrez.miguel.challenge.cardholder.application.usecases.CreateCardHolderService;
import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.dto.CardholderRequest;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.dto.CardholderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing cardholders in the system.
 * Provides endpoints for creating and managing cardholder accounts.
 */
@RestController
@RequestMapping("/cardholders")
@RequiredArgsConstructor
@Tag(name = "Cardholder", description = "Cardholder management APIs")
public class CardholderController {

    private final CreateCardHolderService createCardHolderService;

    /**
     * Creates a new cardholder account.
     *
     * @param request The cardholder creation request containing name and email
     * @return ResponseEntity containing the created cardholder information
     */
    @Operation(
        summary = "Create a new cardholder",
        description = "Creates a new cardholder account with the provided name and email"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Cardholder created successfully",
            content = @Content(schema = @Schema(implementation = CardholderResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters"
        )
    })
    @PostMapping
    public ResponseEntity<CardholderResponse> create(
            @Parameter(description = "Cardholder creation details", required = true)
            @RequestBody CardholderRequest request) {
        Cardholder cardholder = createCardHolderService.create(request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CardholderResponse(
                        cardholder.getId(),
                        cardholder.getName(),
                        cardholder.getEmail()
                )
        );
    }
}
