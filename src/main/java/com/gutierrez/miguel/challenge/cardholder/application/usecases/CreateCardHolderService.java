package com.gutierrez.miguel.challenge.cardholder.application.usecases;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.model.vo.Balance;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCardHolderService {

    private final CardholderRepositoryPort repository;

    public Cardholder create(String name, String email) {
        Cardholder cardholder = Cardholder.builder()
                .id(UUID.randomUUID())
                .name(name)
                .email(email)
                .balance(new Balance(BigDecimal.ZERO))
                .build();
        return repository.save(cardholder);
    }
}
