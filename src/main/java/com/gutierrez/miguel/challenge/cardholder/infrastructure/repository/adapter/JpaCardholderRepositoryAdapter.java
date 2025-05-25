package com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.entity.CardholderEntity;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.mapper.CardholderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaCardholderRepositoryAdapter implements CardholderRepositoryPort {

    private final JpaCardholderRepository repository;
    private final CardholderMapper cardholderMapper;

    @Override
    public Cardholder save(Cardholder cardholder) {
        CardholderEntity entity = cardholderMapper.toEntity(cardholder);

        CardholderEntity savedCardholderEntity = repository.save(entity);
        return cardholderMapper.toDomain(savedCardholderEntity);
    }
}
