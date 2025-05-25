package com.gutierrez.miguel.challenge.transaction.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.transaction.infrastructure.repository.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
}
