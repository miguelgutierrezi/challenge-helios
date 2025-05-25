package com.gutierrez.miguel.challenge.cardholder.application.usecases;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetBalanceService {

    private final CardholderRepositoryPort cardholderRepository;

    public BigDecimal getBalanceById(UUID id) {
        return cardholderRepository.findById(id).getBalance().value();
    }

}
