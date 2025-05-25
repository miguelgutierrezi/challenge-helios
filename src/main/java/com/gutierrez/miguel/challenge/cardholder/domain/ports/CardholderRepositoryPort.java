package com.gutierrez.miguel.challenge.cardholder.domain.ports;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;

import java.util.List;
import java.util.UUID;

public interface CardholderRepositoryPort {
    Cardholder save(Cardholder cardholder);
    Cardholder findById(UUID id);
    List<Cardholder> findAll();
}
