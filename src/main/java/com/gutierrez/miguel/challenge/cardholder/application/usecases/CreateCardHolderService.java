package com.gutierrez.miguel.challenge.cardholder.application.usecases;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCardHolderService {

    private final CardholderRepositoryPort repository;

    public Cardholder create(String name, String email) {
        Cardholder cardholder = Cardholder.builder()
                .id(UUID.randomUUID())
                .name(name)
                .email(email)
                .build();
        return repository.save(cardholder);
    }
}
