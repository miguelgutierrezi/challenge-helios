package com.gutierrez.miguel.challenge.transaction.application.usecases;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.model.vo.Balance;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import com.gutierrez.miguel.challenge.transaction.domain.model.Transaction;
import com.gutierrez.miguel.challenge.transaction.domain.model.vo.TransactionAmount;
import com.gutierrez.miguel.challenge.transaction.domain.ports.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateTransactionService {

    private final TransactionRepositoryPort transactionRepository;
    private final CardholderRepositoryPort cardholderRepository;

    public Transaction execute(UUID cardholderId, BigDecimal amount, String description) {
        Cardholder cardholder = cardholderRepository.findById(cardholderId);

        cardholder.setBalance(new Balance(cardholder.getBalance().value().add(amount)));
        cardholderRepository.save(cardholder);

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .cardholderId(cardholderId)
                .amount(new TransactionAmount(amount))
                .description(description)
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

}
