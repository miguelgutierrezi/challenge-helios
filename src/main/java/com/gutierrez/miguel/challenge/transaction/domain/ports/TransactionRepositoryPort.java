package com.gutierrez.miguel.challenge.transaction.domain.ports;

import com.gutierrez.miguel.challenge.transaction.domain.model.Transaction;

public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);

}
