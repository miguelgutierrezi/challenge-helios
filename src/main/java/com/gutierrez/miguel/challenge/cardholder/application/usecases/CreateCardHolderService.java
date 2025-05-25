package com.gutierrez.miguel.challenge.cardholder.application.usecases;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for creating new cardholder accounts.
 * This service handles the business logic for creating and persisting new cardholder records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCardHolderService {

    private final CardholderRepositoryPort repository;

    /**
     * Creates a new cardholder account with the provided information.
     * Generates a new UUID for the cardholder and persists the record.
     *
     * @param name The full name of the cardholder
     * @param email The email address of the cardholder
     * @return The created Cardholder entity with its generated ID
     */
    public Cardholder create(String name, String email) {
        Cardholder cardholder = Cardholder.builder()
                .id(UUID.randomUUID())
                .name(name)
                .email(email)
                .build();
        return repository.save(cardholder);
    }
}
