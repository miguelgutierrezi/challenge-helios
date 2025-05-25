package com.gutierrez.miguel.challenge.cardholder.domain.ports;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;

public interface CardholderRepositoryPort {
    Cardholder save(Cardholder cardholder);
}
