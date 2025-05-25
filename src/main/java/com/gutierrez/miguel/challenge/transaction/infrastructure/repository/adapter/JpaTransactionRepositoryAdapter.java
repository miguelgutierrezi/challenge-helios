package com.gutierrez.miguel.challenge.transaction.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.transaction.domain.model.Transaction;
import com.gutierrez.miguel.challenge.transaction.domain.ports.TransactionRepositoryPort;
import com.gutierrez.miguel.challenge.transaction.infrastructure.repository.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaTransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final JpaTransactionRepository repository;
    private final TransactionMapper mapper;

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(repository.save(mapper.toEntity(transaction)));
    }
}
