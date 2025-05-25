package com.gutierrez.miguel.challenge.cardholder.infrastructure.controller;

import com.gutierrez.miguel.challenge.cardholder.application.usecases.CreateCardHolderService;
import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.dto.CardholderRequest;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.dto.CardholderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cardholders")
@RequiredArgsConstructor
public class CardholderController {

    private final CreateCardHolderService createCardHolderService;

    @PostMapping
    public ResponseEntity<CardholderResponse> create(@RequestBody CardholderRequest request) {
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
