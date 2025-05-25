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
        log.info("Creating new cardholder account - name: {}, email: {}", name, email);
        
        try {
            UUID cardholderId = UUID.randomUUID();
            log.debug("Generated new UUID for cardholder: {}", cardholderId);
            
            Cardholder cardholder = Cardholder.builder()
                    .id(cardholderId)
                    .name(name)
                    .email(email)
                    .build();
            log.debug("Cardholder object created with ID: {}", cardholder.getId());
            
            Cardholder savedCardholder = repository.save(cardholder);
            log.info("Successfully created cardholder account - ID: {}, name: {}, email: {}", 
                    savedCardholder.getId(), savedCardholder.getName(), savedCardholder.getEmail());
            
            return savedCardholder;
        } catch (Exception e) {
            log.error("Error creating cardholder account - name: {}, email: {}, error: {}", 
                    name, email, e.getMessage(), e);
            throw e;
        }
    }
}
