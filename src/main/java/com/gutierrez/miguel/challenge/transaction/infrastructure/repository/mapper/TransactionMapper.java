package com.gutierrez.miguel.challenge.transaction.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.entity.CardholderEntity;
import com.gutierrez.miguel.challenge.transaction.domain.model.Transaction;
import com.gutierrez.miguel.challenge.transaction.domain.model.vo.TransactionAmount;
import com.gutierrez.miguel.challenge.transaction.infrastructure.repository.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionEntity toEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .description(transaction.getDescription())
                .cardholder(CardholderEntity.builder().id(transaction.getCardholderId()).build())
                .amount(transaction.getAmount().value())
                .build();
    }

    public Transaction toDomain(TransactionEntity transactionEntity) {
        return Transaction.builder()
                .id(transactionEntity.getId())
                .date(transactionEntity.getDate())
                .description(transactionEntity.getDescription())
                .cardholderId(transactionEntity.getCardholder().getId())
                .amount(new TransactionAmount(transactionEntity.getAmount()))
                .build();
    }

}
