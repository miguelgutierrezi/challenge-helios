package com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.cardholder.domain.exception.CardholderNotFoundException;
import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.entity.CardholderEntity;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.mapper.CardholderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Adapter implementation for the CardholderRepositoryPort interface.
 * This class handles the persistence of cardholder data using JPA.
 * It converts between domain models and JPA entities using the CardholderMapper.
 */
@Component
@RequiredArgsConstructor
public class JpaCardholderRepositoryAdapter implements CardholderRepositoryPort {

    private final JpaCardholderRepository repository;
    private final CardholderMapper cardholderMapper;

    /**
     * Saves a cardholder to the database.
     * Converts the domain model to a JPA entity, persists it, and converts it back.
     *
     * @param cardholder The cardholder domain model to be saved
     * @return The saved cardholder with any database-generated fields
     */
    @Override
    public Cardholder save(Cardholder cardholder) {
        CardholderEntity entity = cardholderMapper.toEntity(cardholder);
        CardholderEntity savedCardholderEntity = repository.save(entity);
        return cardholderMapper.toDomain(savedCardholderEntity);
    }

    /**
     * Retrieves a cardholder by their ID.
     * Throws CardholderNotFoundException if no cardholder is found.
     *
     * @param id The UUID of the cardholder to find
     * @return The found cardholder
     * @throws CardholderNotFoundException if no cardholder exists with the given ID
     */
    @Override
    public Cardholder findById(UUID id) {
        return repository.findById(id)
                .map(cardholderMapper::toDomain)
                .orElseThrow(() -> new CardholderNotFoundException("Cardholder with id: %d not found", id));
    }

    /**
     * Retrieves all cardholders from the database.
     * Converts all JPA entities to domain models.
     *
     * @return List of all cardholders in the system
     */
    @Override
    public List<Cardholder> findAll() {
        return repository.findAll().stream()
                .map(cardholderMapper::toDomain)
                .toList();
    }
}
