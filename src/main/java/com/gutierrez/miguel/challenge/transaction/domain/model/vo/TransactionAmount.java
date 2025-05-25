package com.gutierrez.miguel.challenge.transaction.domain.model.vo;

import java.math.BigDecimal;

public record TransactionAmount(BigDecimal value) {
    public TransactionAmount {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount must be non-null and non-negative");
        }
    }
}
